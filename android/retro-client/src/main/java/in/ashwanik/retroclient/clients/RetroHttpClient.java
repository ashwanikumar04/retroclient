package in.ashwanik.retroclient.clients;

import android.content.Context;

import java.io.File;
import java.util.concurrent.TimeUnit;

import in.ashwanik.retroclient.RetroClientServiceInitializer;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * This class initializes OkHttp client.
 */
public class RetroHttpClient {
    private static RetroHttpClient instance = new RetroHttpClient();
    private OkHttpClient okHttpClient;

    private RetroHttpClient() {

    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static RetroHttpClient getInstance() {
        return instance;
    }

    /**
     * Initialize.
     *
     * @param context the context
     */
    public void initialize(Context context) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        if (RetroClientServiceInitializer.getInstance().isDebug()) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);
        }
        try {
            File httpCacheDirectory = new File(context.getCacheDir(), "retro-client-cache");
            int cacheSize = 10 * 1024 * 1024; // 10 MiB
            Cache cache = new Cache(httpCacheDirectory, cacheSize);
            httpClient.cache(cache);
        } catch (Exception exception) {
            RetroClientServiceInitializer.getInstance().getLogger().log(exception);
        }
        int timeOut = RetroClientServiceInitializer.getInstance().getTimeOut();
        httpClient.connectTimeout(timeOut, TimeUnit.SECONDS);
        httpClient.readTimeout(timeOut, TimeUnit.SECONDS);
        httpClient.writeTimeout(timeOut, TimeUnit.SECONDS);
        httpClient.retryOnConnectionFailure(RetroClientServiceInitializer.getInstance().getEnableRetry());
        getInstance().okHttpClient = httpClient.build();
    }

    /**
     * Gets ok http client.
     *
     * @return the ok http client
     */
    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }


}
