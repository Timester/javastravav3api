package javastrava.service.exception;

import javastrava.model.StravaResponse;

/**
 * <p>
 * Implementations of this interface will have the error message emitted by the Strava API available via the {@link #getResponse()}
 * method
 * </p>
 * 
 * @author Dan Shannon
 *
 */
public interface StravaAPIException {
	/**
	 * Return the error message emitted by the Strava API
	 * 
	 * @return the response
	 */
    StravaResponse getResponse();

	/**
	 * @param response
	 *            the response to set
	 */
    void setResponse(final StravaResponse response);
}
