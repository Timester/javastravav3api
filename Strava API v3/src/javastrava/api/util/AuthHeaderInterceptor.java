package javastrava.api.util;

import javastrava.auth.model.Token;
import javastrava.config.StravaConfig;
import lombok.AllArgsConstructor;
import okhttp3.Interceptor;
import okhttp3.Request;

import java.io.IOException;

@AllArgsConstructor
public class AuthHeaderInterceptor implements Interceptor {

    private final Token token;

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request newRequest = request.newBuilder()
                .addHeader(StravaConfig.string("strava.authorization_header_name"), token.getTokenType() + " " + token.getAccessToken())
                .build();

        return chain.proceed(newRequest);
    }
}
