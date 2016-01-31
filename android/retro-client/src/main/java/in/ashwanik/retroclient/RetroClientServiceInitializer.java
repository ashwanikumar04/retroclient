package in.ashwanik.retroclient;

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
    private CallAdapter.Factory callAdapterFactory;
    private Converter.Factory converterFactory;
    private Integer timeOut;
    private Boolean enableRetry;

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
    public void initialize(String baseUrl) {
        this.baseUrl = baseUrl;

    }

    /**
     * Gets call adapter factory.
     *
     * @return the call adapter factory
     */
    public CallAdapter.Factory getCallAdapterFactory() {
        if (callAdapterFactory == null) {
            callAdapterFactory = new ErrorHandlingCallAdapterFactory();
        }
        return callAdapterFactory;
    }

    /**
     * Sets call adapter factory.
     *
     * @param callAdapterFactory the call adapter factory
     */
    public void setCallAdapterFactory(CallAdapter.Factory callAdapterFactory) {
        this.callAdapterFactory = callAdapterFactory;
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
