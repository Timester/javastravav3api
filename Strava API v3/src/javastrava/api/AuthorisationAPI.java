package javastrava.api;

import javastrava.auth.model.TokenResponse;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public interface AuthorisationAPI {
	@FormUrlEncoded
	@POST("/oauth/token")
	TokenResponse tokenExchange(@Field("client_id") final Integer clientId, @Field("client_secret") final String clientSecret,
			@Field("code") final String code, @Field("grant_type") final String grantType);

	@FormUrlEncoded
	@POST("/oauth/token")
	TokenResponse getAuthToken(@Field("client_id") final Integer clientId, @Field("client_secret") final String clientSecret,
							   @Field("refresh_token") final String refreshToken, @Field("grant_type") final String grantType);
}
