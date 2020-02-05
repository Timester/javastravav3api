package javastrava.auth.impl;

import javastrava.api.API;
import javastrava.api.AuthorisationAPI;
import javastrava.auth.AuthorisationService;
import javastrava.auth.TokenManager;
import javastrava.auth.model.Token;
import javastrava.auth.model.TokenResponse;
import javastrava.auth.ref.AuthorisationScope;

import java.time.Instant;

public class AuthorisationServiceImpl implements AuthorisationService {

	private static final String GRANT_TYPE_REFRESH_TOKEN = "refresh_token";
	private static final String GRANT_TYPE_AUTH_CODE = "authorization_code";

	private final AuthorisationAPI api;
	private final TokenManager tokenManager;

	public AuthorisationServiceImpl() {
		this.api = API.authorisationInstance();
		this.tokenManager = TokenManager.instance();
	}

	@Override
	public Token tokenExchange(final Integer clientId, final String clientSecret, final String code, final AuthorisationScope... scopes) {
		final TokenResponse response = this.api.tokenExchange(clientId, clientSecret, code, GRANT_TYPE_AUTH_CODE);
		final Token token = new Token(response, scopes);
		TokenManager.instance().storeToken(token);
		return token;
	}

	@Override
	public Token getTokenForAuthorizedUser(final Integer clientId, final String clientSecret, Integer userId, String refreshToken) {
		Token cachedToken = tokenManager.retrieveToken(userId);

		if (cachedToken == null || isExpired(cachedToken)) {
			Token newToken = getNewToken(clientId, clientSecret, refreshToken);
			if (cachedToken == null) {
				cachedToken = newToken;
			} else {
				cachedToken.setAccessToken(newToken.getAccessToken());
				cachedToken.setRefreshToken(newToken.getRefreshToken());
				cachedToken.setExpiresAt(newToken.getExpiresAt());
			}
		}

		return cachedToken;
	}

	private Token getNewToken(final Integer clientId, final String clientSecret, final String refreshToken) {
		TokenResponse newTokenResponse = this.api.getAuthToken(clientId, clientSecret, refreshToken, GRANT_TYPE_REFRESH_TOKEN);
		return new Token(newTokenResponse);
	}

	private boolean isExpired(Token token) {
		return token.getExpiresAt() < Instant.now().getEpochSecond();
	}
}
