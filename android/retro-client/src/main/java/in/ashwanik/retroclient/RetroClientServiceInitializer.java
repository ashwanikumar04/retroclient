package in.ashwanik.retroclient;

import android.content.Context;
import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;

import in.ashwanik.retroclient.clients.RetroHttpClient;
import in.ashwanik.retroclient.interfaces.ILogger;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.GsonConverterFactory;


/**
 * The type Retro client service initializer.
 */
public class RetroClientServiceInitializer {
    private static RetroClientServiceInitializer instance = new RetroClientServiceInitializer();
    /**
     * The Base url.
     */
    String baseUrl;
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

    public String getCacheDirectory() {
        return cacheDirectory;
    }

    public void setCacheDirectory(String cacheDirectory) {
        this.cacheDirectory = cacheDirectory;
    }

    public String getLogCategoryName() {
        if (logCategoryName == null || logCategoryName.isEmpty()) {
            logCategoryName = "Retro-Client";
        }
        return logCategoryName;
    }

    public void setLogCategoryName(String logCategoryName) {
        this.logCategoryName = logCategoryName;
    }

    public int getProgressViewColor() {
        return progressViewColor;
    }

    public void setProgressViewColor(int progressViewColor) {
        this.progressViewColor = progressViewColor;
    }

    public ILogger getLogger() {
        if (logger == null) {
            return new ILogger() {
                @Override
                public void log(String message) {
                    Log.d(getLogCategoryName(), message);
                }

                @Override
                public void log(Exception exception) {
                    StringWriter errors = new StringWriter();
                    exception.printStackTrace(new PrintWriter(errors));
                    Log.e(getLogCategoryName(), errors.toString());
                }
            };
        }
        return logger;
    }

    public void setLogger(ILogger logger) {
        this.logger = logger;
    }

    public Boolean isDebug() {
        if (isDebug == null) {
            isDebug = false;
        }
        return isDebug;
    }

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
     * @param baseUrl the base url
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
