package in.ashwanik.retroclient;

import android.content.Context;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import in.ashwanik.retroclient.clients.RetroHttpClient;
import in.ashwanik.retroclient.entities.DefaultData;
import in.ashwanik.retroclient.interfaces.ILogger;
import in.ashwanik.retroclient.utils.Helpers;
import okhttp3.Interceptor;
import retrofit2.Converter;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Retro client service initializer
 */
public class RetroClientServiceInitializer {
    private static RetroClientServiceInitializer instance = new RetroClientServiceInitializer();
    private String baseUrl;
    private Converter.Factory converterFactory;
    private int timeOut;
    private boolean enableRetry;
    private boolean isDebug;
    private String logCategoryName;
    private int progressViewColor;
    private ILogger logger;
    private File cacheDirectory;
    private DefaultData defaultData;
    private int cacheSize;
    private List<Interceptor> networkInterceptors;
    private List<Interceptor> applicationInterceptors;

    private RetroClientServiceInitializer() {

    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static RetroClientServiceInitializer getInstance() {
        return instance;
    }

    /**
     * Gets the list of network interceptors.
     *
     * @return List of Interceptor
     */
    public List<Interceptor> getNetworkInterceptors() {
        return networkInterceptors;
    }

    /**
     * Add network interceptor.
     *
     * @param networkInterceptor Network interceptor
     */
    private void addNetworkInterceptor(Interceptor networkInterceptor) {
        this.networkInterceptors.add(networkInterceptor);
    }

    /**
     * Gets the list of application interceptors.
     *
     * @return List of Interceptor
     */
    public List<Interceptor> getApplicationInterceptors() {
        return applicationInterceptors;
    }

    /**
     * Add application interceptor.
     *
     * @param applicationInterceptor Application interceptor
     */
    private void addApplicationInterceptor(Interceptor applicationInterceptor) {
        this.applicationInterceptors.add(applicationInterceptor);
    }

    public DefaultData getDefaultData() {
        return defaultData;
    }

    public void setDefaultData(DefaultData defaultData) {
        this.defaultData = defaultData;
    }

    /**
     * Gets cache directory.
     *
     * @return the cache directory
     */
    public File getCacheDirectory() {
        return cacheDirectory;
    }


    /**
     * Gets log category name.
     *
     * @return the log category name
     */
    public String getLogCategoryName() {
        return logCategoryName;
    }

    /**
     * Sets log category name.
     *
     * @param logCategoryName the log category name
     */
    public void setLogCategoryName(String logCategoryName) {
        this.logCategoryName = logCategoryName;
    }

    /**
     * Gets progress view color.
     *
     * @return the progress view color
     */
    public int getProgressViewColor() {
        return progressViewColor;
    }

    /**
     * Gets logger.
     *
     * @return the logger
     */
    public ILogger getLogger() {
        return logger;
    }

    /**
     * Sets logger.
     *
     * @param logger the logger
     */
    public void setLogger(ILogger logger) {
        this.logger = logger;
    }

    /**
     * Is debug boolean.
     *
     * @return the boolean
     */
    public boolean isDebug() {
        return isDebug;
    }

    /**
     * Gets time out.
     *
     * @return the time out
     */
    public int getTimeOut() {
        return timeOut;
    }

    /**
     * Sets time out.
     *
     * @param timeOut the time out
     */
    public void setTimeOut(Integer timeOut) {
        this.timeOut = timeOut;
    }

    /**
     * Gets enable retry.
     *
     * @return the enable retry
     */
    public Boolean getEnableRetry() {
        return enableRetry;
    }

    /**
     * Sets enable retry.
     *
     * @param enableRetry the enable retry
     */
    public void setEnableRetry(Boolean enableRetry) {
        this.enableRetry = enableRetry;
    }

    /**
     * Gets base url.
     *
     * @return the base url
     * @throws Exception the exception
     */
    public String getBaseUrl() throws Exception {
        if (baseUrl == null || baseUrl.isEmpty()) {
            throw new Exception("Base url is not set. Please call initialize to set baseUrl");
        }
        return baseUrl;
    }

    private void initialize(List<Interceptor> localNetworkInterceptors, List<Interceptor> localApplicationInterceptors) {
        this.networkInterceptors = new ArrayList<>();
        this.applicationInterceptors = new ArrayList<>();

        defaultData = new DefaultData.Builder()
                .defaultResponseCode(0)
                .genericErrorMessage("Some error occurred.")
                .noInternetErrorMessage("Please check network connection.")
                .build();
        logCategoryName = "Retro-Client";
        enableRetry = true;
        timeOut = 30;
        converterFactory = GsonConverterFactory.create();
        logger = new ILogger() {
            @Override
            public void log(String message) {
                Helpers.d(getLogCategoryName(), message);
            }

            @Override
            public void log(Exception exception) {
                Helpers.e(getLogCategoryName(), Helpers.exceptionToString(exception));
            }
        };
        if (localNetworkInterceptors != null) {
            for (Interceptor interceptor : localNetworkInterceptors) {
                addNetworkInterceptor(interceptor);
            }
        }
        if (localApplicationInterceptors != null) {
            for (Interceptor interceptor : localApplicationInterceptors) {
                addApplicationInterceptor(interceptor);
            }
        }

    }

    /**
     * Initialize.
     *
     * @param baseUrl           the base url
     * @param context           the context
     * @param progressViewColor the progress view color
     */
    public void initialize(String baseUrl, Context context, int progressViewColor) {
        initialize(baseUrl, context, progressViewColor, false);
    }


    /**
     * Initialize.
     *
     * @param baseUrl           the base url
     * @param context           the context
     * @param progressViewColor the progress view color
     * @param isDebug           is debug
     */
    public void initialize(String baseUrl, Context context, int progressViewColor, boolean isDebug) {
        initialize(baseUrl, context, progressViewColor, isDebug, 10 * 1024 * 1024);
    }

    /**
     * Initialize.
     *
     * @param baseUrl           the base url
     * @param context           the context
     * @param progressViewColor the progress view color
     * @param isDebug           is debug
     * @param cacheSize         cache size
     */
    public void initialize(String baseUrl, Context context, int progressViewColor, boolean isDebug, int cacheSize) {
        initialize(baseUrl, progressViewColor, isDebug, cacheSize, context.getCacheDir());
    }

    /**
     * Initialize.
     *
     * @param baseUrl           the base url
     * @param progressViewColor the progress view color
     * @param isDebug           is debug
     * @param cacheSize         cache size
     * @param cacheDirectory    cache directory
     */
    public void initialize(String baseUrl, int progressViewColor, boolean isDebug, int cacheSize, File cacheDirectory) {
        initialize(baseUrl, progressViewColor, isDebug, cacheSize, cacheDirectory, new ArrayList<Interceptor>(), new ArrayList<Interceptor>());
    }

    /**
     * Initialize.
     *
     * @param baseUrl           the base url
     * @param progressViewColor the progress view color
     * @param isDebug           is debug
     * @param cacheSize         cache size
     * @param cacheDirectory    cache directory
     */
    public void initialize(String baseUrl, int progressViewColor, boolean isDebug, int cacheSize, File cacheDirectory, List<Interceptor> networkInterceptors, List<Interceptor> applicationInterceptors) {
        this.baseUrl = baseUrl;
        this.cacheSize = cacheSize;
        this.isDebug = isDebug;
        this.cacheDirectory = cacheDirectory;
        this.progressViewColor = progressViewColor;
        initialize(networkInterceptors, applicationInterceptors);
        RetroHttpClient.getInstance().initialize();
    }


    /**
     * Gets converter factory.
     *
     * @return the converter factory
     */
    public Converter.Factory getConverterFactory() {
        return converterFactory;
    }

    /**
     * Sets converter factory.
     *
     * @param converterFactory the converter factory
     */
    public void setConverterFactory(Converter.Factory converterFactory) {
        this.converterFactory = converterFactory;
    }

    /**
     * Get cache size.
     *
     * @return Cache size
     */
    public int getCacheSize() {
        return cacheSize;
    }
}
