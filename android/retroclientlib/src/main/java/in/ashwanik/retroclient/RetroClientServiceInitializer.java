package in.ashwanik.retroclient;

import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.GsonConverterFactory;

/**
 * Created by AshwaniK on 1/31/2016.
 */
public class RetroClientServiceInitializer {
    private static RetroClientServiceInitializer instance = new RetroClientServiceInitializer();
    String baseUrl;
    private CallAdapter.Factory callAdapterFactory;
    private Converter.Factory converterFactory;
    private Integer timeOut;
    private Boolean enableRetry;

    private RetroClientServiceInitializer() {

    }

    public static RetroClientServiceInitializer getInstance() {
        return instance;
    }


    public Integer getTimeOut() {
        if (timeOut == null) {
            timeOut = 30;
        }
        return timeOut;
    }

    public void setTimeOut(Integer timeOut) {
        this.timeOut = timeOut;
    }

    public Boolean getEnableRetry() {
        if (enableRetry == null) {
            enableRetry = true;
        }
        return enableRetry;
    }

    public void setEnableRetry(Boolean enableRetry) {
        this.enableRetry = enableRetry;
    }

    public String getBaseUrl() throws Exception {
        if (baseUrl == null || baseUrl.isEmpty()) {
            throw new Exception("Base url is not set. Please call initialize to set baseUrl");
        }
        return baseUrl;
    }

    public void initialize(String baseUrl) {
        this.baseUrl = baseUrl;

    }

    public CallAdapter.Factory getCallAdapterFactory() {
        if (callAdapterFactory == null) {
            callAdapterFactory = new ErrorHandlingCallAdapterFactory();
        }
        return callAdapterFactory;
    }

    public void setCallAdapterFactory(CallAdapter.Factory callAdapterFactory) {
        this.callAdapterFactory = callAdapterFactory;
    }

    public Converter.Factory getConverterFactory() {
        if (converterFactory == null) {
            converterFactory = GsonConverterFactory.create();
        }
        return converterFactory;
    }

    public void setConverterFactory(Converter.Factory converterFactory) {
        this.converterFactory = converterFactory;
    }
}
