package in.ashwanik.retroclient.service;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import in.ashwanik.retroclient.RetroClientServiceInitializer;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

public class ServiceGenerator {

    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null);
    }

    public static <S> S createService(Class<S> serviceClass, final Map<String, String> headers) {
        return createService(serviceClass, headers, false);
    }

    public static <S> S createService(Class<S> serviceClass, final Map<String, String> headers, final boolean isDebug) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder;
        try {
            builder = new Retrofit.Builder()
                    .baseUrl(RetroClientServiceInitializer.getInstance().getBaseUrl())
                    .addCallAdapterFactory(RetroClientServiceInitializer.getInstance().getCallAdapterFactory())
                    .addConverterFactory(RetroClientServiceInitializer.getInstance().getConverterFactory());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        if (isDebug) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);
        }
        if (headers != null && !headers.isEmpty()) {
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Interceptor.Chain chain) throws IOException {
                    Request original = chain.request();
                    Request.Builder requestBuilder = original.newBuilder();

                    for (String header : headers.keySet()) {
                        requestBuilder.header(header, headers.get(header));
                    }

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
        }
        int timeOut = RetroClientServiceInitializer.getInstance().getTimeOut();
        httpClient.connectTimeout(timeOut, TimeUnit.SECONDS);
        httpClient.readTimeout(timeOut, TimeUnit.SECONDS);
        httpClient.writeTimeout(timeOut, TimeUnit.SECONDS);
        httpClient.retryOnConnectionFailure(RetroClientServiceInitializer.getInstance().getEnableRetry());
        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }
}

