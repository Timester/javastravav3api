package javastrava.api.util;

import java.io.IOException;
import java.util.StringTokenizer;


import javastrava.config.StravaConfig;
import javastrava.service.Strava;
import okhttp3.Interceptor;

public class StravaAPIRateLimitInterceptor implements Interceptor {

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        okhttp3.Response response = chain.proceed(chain.request());

        String usageHeader = response.header(StravaConfig.string("strava.rate-limit-usage-header-name"));
        String limitHeader = response.header(StravaConfig.string("strava.rate-limit-limit-header-name"));

        if (usageHeader != null && !usageHeader.isEmpty()) {
            StringTokenizer tokenizer = new StringTokenizer(usageHeader, ","); 
            Strava.REQUEST_RATE_CURRENT = Integer.parseInt(tokenizer.nextToken());
            Strava.REQUEST_RATE_DAILY = Integer.parseInt(tokenizer.nextToken());
            Strava.requestRateCurrentPercentage();
        }
        if (limitHeader != null && !limitHeader.isEmpty()) {
            StringTokenizer tokenizer = new StringTokenizer(limitHeader, ","); 
            Strava.RATE_LIMIT_CURRENT = Integer.parseInt(tokenizer.nextToken());
            Strava.RATE_LIMIT_DAILY = Integer.parseInt(tokenizer.nextToken());
            Strava.requestRateDailyPercentage();
        }

        return response;
    }
}
