package javastrava.auth;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javastrava.auth.model.Token;
import javastrava.auth.ref.AuthorisationScope;
import javastrava.config.Messages;

public class TokenManager {
    private static final TokenManager instance = new TokenManager();

    public static TokenManager instance() {
        return instance;
    }

    /**
     * Cached tokens, mapped by userId
     */
    private final Map<Integer, Token> tokens;

    private TokenManager() {
        this.tokens = new HashMap<>();
    }

    public void clearTokenCache() {
        for (final Token token : this.tokens.values()) {
            revokeToken(token);
        }
    }

    public Token retrieveToken(final int userId) {
        final Token token = this.tokens.get(userId);

        // If there's no such token, or it has no scopes, then return null
        if ((token == null) || (token.getScopes() == null)) {
            return null;
        }

        return token;
    }

    /**
     * <p>
     * Retrieve a cached token which has <strong>exactly</strong> the given set
     * of scopes
     * </p>
     *
     * @param userId         The user id
     * @param requiredScopes This list of scopes which must match the scopes of the token
     * @return The token with the matching list of scopes, or <code>null</code>
     * if there is no such token
     */
    public Token retrieveTokenWithExactScope(final int userId, final AuthorisationScope... requiredScopes) {
        // Get the token from the cache
        final Token token = this.tokens.get(userId);

        // If there's no such token, or it has no scopes, then return null
        if ((token == null) || (token.getScopes() == null)) {
            return null;
        }

        // Check that all the required scopes are in the token
        for (final AuthorisationScope scope : requiredScopes) {
            if (!token.getScopes().contains(scope)) {
                return null;
            }
        }

        // Check that all the scopes in the token are required
        for (final AuthorisationScope scope : token.getScopes()) {
            boolean match = false;
            for (final AuthorisationScope requiredScope : requiredScopes) {
                if (scope == requiredScope) {
                    match = true;
                    break;
                }
            }
            if (!match) {
                return null;
            }
        }

        // OK we're happy now, so return the token
        return token;
    }

    /**
     * <p>
     * Retrieve a cached token which has <strong>exactly</strong> the given set
     * of scopes
     * </p>
     *
     * @param userId The user to look up for a cached token
     * @param scopes The set of scopes the token must have
     * @return The matching token from the cache, or <code>null</code> if there is no matching token
     */
    public Token retrieveTokenWithExactScope(final int userId, final List<AuthorisationScope> scopes) {
        if (scopes == null) {
            return retrieveTokenWithExactScope(userId);
        }
        final AuthorisationScope[] array = new AuthorisationScope[scopes.size()];
        for (int i = 0; i < scopes.size(); i++) {
            array[i] = scopes.get(i);
        }
        return retrieveTokenWithExactScope(userId, array);
    }

    /**
     * <p>
     * Retrieve a token which has <strong>at least</strong> the given set of
     * scopes.
     * </p>
     *
     * @param userId The userId
     * @param scopes The list of scopes which are required to be in the token
     * @return The token, or <code>null</code> if there is no cached token, or
     * the cached token doesn't have all the required scopes
     */
    public Token retrieveTokenWithScope(final int userId, final AuthorisationScope... scopes) {
        // Get the token from cache
        final Token token = this.tokens.get(userId);
        AuthorisationScope[] authScopes = scopes;

        // If scopes = null
        if (authScopes == null) {
            authScopes = new AuthorisationScope[0];
        }

        // If there's no cached token, or it doesn't have any scopes (which
        // shouldn't happen) then return null
        if ((token == null) || (token.getScopes() == null)) {
            return null;
        }

        // Check that all the required scopes are in the token
        for (final AuthorisationScope scope : authScopes) {
            if (!token.getScopes().contains(scope)) {
                return null;
            }
        }
        return token;
    }

    /**
     * <p>
     * Retrieve a token which has <strong>at least</strong> the given set of
     * scopes.
     * </p>
     *
     * @param userId The userId
     * @param scopes The list of scopes which are required to be in the token
     * @return The token, or <code>null</code> if there is no cached token, or
     * the cached token doesn't have all the required scopes
     */
    public Token retrieveTokenWithScope(final int userId, final List<AuthorisationScope> scopes) {

        if (scopes == null) {
            return retrieveTokenWithScope(userId);
        }
        final AuthorisationScope[] array = new AuthorisationScope[scopes.size()];
        for (int i = 0; i < scopes.size(); i++) {
            array[i] = scopes.get(i);
        }
        return retrieveTokenWithExactScope(userId, array);
    }

    /**
     * <p>
     * Revoke an access token - that is, remove it from the cache of tokens.
     * </p>
     *
     * @param token The token to be removed from the cache
     */
    public void revokeToken(final Token token) {
        this.tokens.remove(token.getAthlete().getId());
    }

    /**
     * <p>
     * Place a token in the cache
     * </p>
     *
     * @param token The token to be stored in the cache.
     * @throws IllegalArgumentException If the token is null, or the athlete contained in it is null or has a null email, or there are no authorisation scopes, then
     */
    public void storeToken(final Token token) {
        Integer userId;
        if (token == null) {
            throw new IllegalArgumentException(Messages.string("TokenManager.0")); //$NON-NLS-1$
        }

        if (token.getAthlete() == null) {
            throw new IllegalArgumentException(Messages.string("TokenManager.1")); //$NON-NLS-1$
        }
        if (token.getAthlete().getId() == null) {
            throw new IllegalArgumentException(Messages.string("TokenManager.2")); //$NON-NLS-1$
        }
        if (token.getScopes() == null) {
            throw new IllegalArgumentException(Messages.string("TokenManager.3")); //$NON-NLS-1$
        }
        userId = token.getAthlete().getId();
        this.tokens.put(userId, token);
    }
}
