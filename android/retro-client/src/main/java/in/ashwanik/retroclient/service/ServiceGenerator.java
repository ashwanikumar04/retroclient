package in.ashwanik.retroclient.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import in.ashwanik.retroclient.RetroClientServiceInitializer;
import in.ashwanik.retroclient.clients.RetroHttpClient;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;

/**
 * Service generator.
 */
public class ServiceGenerator {

    private static Map<String, Object> clients = new HashMap<>();

    /**
     * Creates service.
     *
     * @param <S>          Type of the service
     * @param serviceClass Class of the service
     * @return service to make network call
     */
    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null);
    }

    /**
     * Create service.
     *
     * @param <S>          Type of the service
     * @param serviceClass Class of the service
     * @param headers      Map of headers
     * @return service to make network call
     */
    public static <S> S createService(Class<S> serviceClass, final Map<String, String> headers) {
        return createService(serviceClass, headers, null);
    }

    /**
     * Create service s.
     *
     * @param <S>          Type of the service
     * @param serviceClass Class of the service
     * @param headers      Map of headers
     * @param serviceName  Service name
     * @return service to make network call
     */
    public static <S> S createService(Class<S> serviceClass, final Map<String, String> headers, String serviceName) {
        String key = serviceName;
        if (key == null || key.isEmpty()) {
            key = serviceClass.getName();

        }

        if (clients.containsKey(key)) {
            return serviceClass.cast(clients.get(key));
        }

        Retrofit.Builder builder;
        try {
            builder = new Retrofit.Builder()
                    .baseUrl(RetroClientServiceInitializer.getInstance().getBaseUrl())
                    .addConverterFactory(RetroClientServiceInitializer.getInstance().getConverterFactory());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        OkHttpClient.Builder httpClientBuilder = RetroHttpClient.getInstance().getOkHttpClient().newBuilder();

        if (headers != null && !headers.isEmpty()) {
            httpClientBuilder.addInterceptor(new Interceptor() {
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

        Retrofit retrofit = builder.client(httpClientBuilder.build()).build();
        S service = retrofit.create(serviceClass);
        clients.put(key, service);
        return service;
    }
}

