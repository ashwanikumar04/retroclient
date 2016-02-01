package in.ashwanik.retroclient.clients;

import java.util.concurrent.TimeUnit;

import in.ashwanik.retroclient.RetroClientServiceInitializer;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by ashwani on 1/2/16.
 */
public class RetroHttpClient {
    private static RetroHttpClient instance = new RetroHttpClient();
    private OkHttpClient okHttpClient;

    private RetroHttpClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        if (RetroClientServiceInitializer.getInstance().isDebug()) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);
        }
        int timeOut = RetroClientServiceInitializer.getInstance().getTimeOut();
        httpClient.connectTimeout(timeOut, TimeUnit.SECONDS);
        httpClient.readTimeout(timeOut, TimeUnit.SECONDS);
        httpClient.writeTimeout(timeOut, TimeUnit.SECONDS);
        httpClient.retryOnConnectionFailure(RetroClientServiceInitializer.getInstance().getEnableRetry());
        getInstance().okHttpClient = httpClient.build();
    }

    public static RetroHttpClient getInstance() {
        return instance;
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }


}
