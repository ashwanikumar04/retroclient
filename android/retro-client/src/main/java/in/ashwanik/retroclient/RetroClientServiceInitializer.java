package in.ashwanik.retroclient;

import android.content.Context;

import java.io.PrintWriter;
import java.io.StringWriter;

import in.ashwanik.retroclient.clients.RetroHttpClient;
import in.ashwanik.retroclient.interfaces.ILogger;
import in.ashwanik.retroclient.utils.Helpers;
import retrofit2.Converter;
import retrofit2.GsonConverterFactory;


/**
 * Retro client service initializer
 */
public class RetroClientServiceInitializer {
    private static RetroClientServiceInitializer instance = new RetroClientServiceInitializer();
    private String baseUrl;
    private Converter.Factory converterFactory;
    private Integer timeOut;
    private Boolean enableRetry;
    private Boolean isDebug;
    private String logCategoryName;
    private int progressViewColor;
    private ILogger logger;
    private String cacheDirectory;

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
     * Gets cache directory.
     *
     * @return the cache directory
     */
    public String getCacheDirectory() {
        return cacheDirectory;
    }

    /**
     * Sets cache directory.
     *
     * @param cacheDirectory the cache directory
     */
    public void setCacheDirectory(String cacheDirectory) {
        this.cacheDirectory = cacheDirectory;
    }

    /**
     * Gets log category name.
     *
     * @return the log category name
     */
    public String getLogCategoryName() {
        if (logCategoryName == null || logCategoryName.isEmpty()) {
            logCategoryName = "Retro-Client";
        }
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
     * Sets progress view color.
     *
     * @param progressViewColor the progress view color
     */
    public void setProgressViewColor(int progressViewColor) {
        this.progressViewColor = progressViewColor;
    }

    /**
     * Gets logger.
     *
     * @return the logger
     */
    public ILogger getLogger() {
        if (logger == null) {
            return new ILogger() {
                @Override
                public void log(String message) {
                    Helpers.d(getLogCategoryName(), message);
                }

                @Override
                public void log(Exception exception) {
                    StringWriter errors = new StringWriter();
                    exception.printStackTrace(new PrintWriter(errors));
                    Helpers.e(getLogCategoryName(), errors.toString());
                }
            };
        }
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
    public Boolean isDebug() {
        if (isDebug == null) {
            isDebug = false;
        }
        return isDebug;
    }

    /**
     * Sets debug.
     *
     * @param isDebug the is debug
     */
    public void setDebug(Boolean isDebug) {
        this.isDebug = isDebug;
    }

    /**
     * Gets time out.
     *
     * @return the time out
     */
    public Integer getTimeOut() {
        if (timeOut == null) {
            timeOut = 30;
        }
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
        if (enableRetry == null) {
            enableRetry = true;
        }
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

    /**
     * Initialize.
     *
     * @param baseUrl           the base url
     * @param context           the context
     * @param progressViewColor the progress view color
     * @param isDebug           the is debug
     */
    public void initialize(String baseUrl, Context context, int progressViewColor, boolean isDebug) {
        this.baseUrl = baseUrl;
        this.setDebug(isDebug);
        RetroHttpClient.getInstance().initialize(context);
        this.progressViewColor = progressViewColor;
    }


    /**
     * Gets converter factory.
     *
     * @return the converter factory
     */
    public Converter.Factory getConverterFactory() {
        if (converterFactory == null) {
            converterFactory = GsonConverterFactory.create();
        }
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
}
