package javastrava.api.util;

import java.io.IOException;

import javastrava.config.Messages;
import javastrava.json.JsonUtil;
import javastrava.json.impl.JsonUtilImpl;
import javastrava.model.StravaAPIError;
import javastrava.model.StravaResponse;
import javastrava.service.exception.BadRequestException;
import javastrava.service.exception.InvalidTokenException;
import javastrava.service.exception.NotFoundException;
import javastrava.service.exception.StravaAPINetworkException;
import javastrava.service.exception.StravaAPIRateLimitException;
import javastrava.service.exception.StravaInternalServerErrorException;
import javastrava.service.exception.StravaServiceUnavailableException;
import javastrava.service.exception.StravaUnknownAPIException;
import javastrava.service.exception.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import retrofit.ErrorHandler;
import retrofit.RetrofitError;
import retrofit.RetrofitError.Kind;
import retrofit.client.Response;

/**
 * <p>
 * Interceptor to handle errors thrown by the Strava API.
 * </p>
 *
 * <p>
 * HTTP error status codes are converted to checked (if recoverable) or unchecked (if not) exceptions and thrown
 * </p>
 *
 * <p>
 * With each error, Strava also returns a set of error messages which are encapsulated in {@link StravaResponse}
 * </p>
 *
 * @author Dan Shannon
 *
 */
@Slf4j
public class RetrofitErrorHandler implements ErrorHandler {

	/**
	 * <p>
	 * Determines whether a token is invalid or not
	 * </p>
	 *
	 * @param response
	 *            response received from the Strava API call
	 * @return <code>true</code> if the token is invalid, <code>false</code> otherwise
	 */
	private static boolean tokenInvalid(final StravaResponse response) {
		if ((response == null) || (response.getErrors() == null) || response.getErrors().isEmpty()) {
			return false;
		}
		for (final StravaAPIError error : response.getErrors()) {
			if (error.getResource().equals("Application") && error.getCode().equals("invalid")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * JSON utilities for serialisation and deserialisation
	 */
	JsonUtil json = new JsonUtilImpl();

	/**
	 * @see retrofit.ErrorHandler#handleError(retrofit.RetrofitError)
	 */
	@Override
	public Throwable handleError(final RetrofitError cause) {
		final Response r = cause.getResponse();
		StravaResponse response = null;
		final String status = (r == null ? Messages.string("RetrofitErrorHandler.unknownError") 
				: r.getStatus() + " " + r.getReason()); 

		// Handle network errors
		if (cause.getKind() == Kind.NETWORK) {
			return new StravaAPINetworkException(null, response, cause);
		}

		if (r == null) {
			return new StravaUnknownAPIException(status, response, cause);
		}

		try {
			if (r.getBody() == null) {
				response = new StravaResponse();
				response.setMessage(cause.getMessage());
			} else {
				response = this.json.deserialise(r.getBody().in(), StravaResponse.class);
			}
		} catch (final IOException e) {
			response = new StravaResponse();
			response.setMessage(r.getBody().toString());
		} catch (final Exception e) {
			response = new StravaResponse();
			response.setMessage(cause.toString());
		}

		// Handle 400 Bad request error
		if (r.getStatus() == 400) {
			log.warn(status + " : " + response); 
			return new BadRequestException(status, response, cause);
		}

		// Handle 401 Unauthorized error
		if (r.getStatus() == 401) {
			if (tokenInvalid(response)) {
				log.info(status + " : " + response); 
				return new InvalidTokenException(status, response, cause);
			}

			log.warn(status + " : " + response); 
			return new UnauthorizedException(status, response, cause);
		}

		// Handle 403 forbidden error
		if (r.getStatus() == 403) {
			log.info(status + " : " + response); 
			if (response.getMessage().equals(Messages.string("RetrofitErrorHandler.rateLimitExceeded"))) { 
				return new StravaAPIRateLimitException(status, response, cause);
			}
			return new UnauthorizedException(status, response, cause);
		}

		// Handle 404 Not Found error
		if (r.getStatus() == 404) {
			log.info(status + " : " + response); 
			return new NotFoundException(response, cause);
		}

		// Handle 429 Too many requests error
		if (r.getStatus() == 429) {
			log.warn(status + " : " + response); 
			return new StravaAPIRateLimitException(status, response, cause);
		}

		// Handle 500 Internal Server error
		if (r.getStatus() == 500) {
			log.error(status + " : " + response); 
			return new StravaInternalServerErrorException(status, response, cause);
		}

		// Handle 503 Service Unavailable error
		if (r.getStatus() == 503) {
			log.error(status + " : " + response); 
			return new StravaServiceUnavailableException(status, response, cause);
		}

		log.error(response.toString());
		return new StravaUnknownAPIException(status, response, cause);
	}

}
