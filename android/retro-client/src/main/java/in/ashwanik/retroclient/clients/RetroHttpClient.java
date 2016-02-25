package in.ashwanik.retroclient.clients;

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
     */
    public void initialize() {
        RetroClientServiceInitializer retroClientServiceInitializer = RetroClientServiceInitializer.getInstance();
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        if (RetroClientServiceInitializer.getInstance().isDebug()) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);
        }
        try {
            File httpCacheDirectory = new File(retroClientServiceInitializer.getCacheDirectory(), "retro-client-cache");
            Cache cache = new Cache(httpCacheDirectory, retroClientServiceInitializer.getCacheSize());
            httpClient.cache(cache);
        } catch (Exception exception) {
            retroClientServiceInitializer.getLogger().log(exception);
        }
        int timeOut = retroClientServiceInitializer.getTimeOut();
        httpClient.connectTimeout(timeOut, TimeUnit.SECONDS);
        httpClient.readTimeout(timeOut, TimeUnit.SECONDS);
        httpClient.writeTimeout(timeOut, TimeUnit.SECONDS);
        httpClient.retryOnConnectionFailure(retroClientServiceInitializer.getEnableRetry());
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
