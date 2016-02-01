package in.ashwanik.retroclient.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import in.ashwanik.retroclient.RetroClientServiceInitializer;
import in.ashwanik.retroclient.clients.BaseRetroClient;
import in.ashwanik.retroclient.clients.RetroHttpClient;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * The type Service generator.
 */
public class ServiceGenerator {

    private static Map<String, BaseRetroClient> clients = new HashMap<>();

    /**
     * Create service s.
     *
     * @param <S>          the type parameter
     * @param serviceClass the service class
     * @return the s
     */
    public static <S extends BaseRetroClient> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null);
    }

    /**
     * Create service s.
     *
     * @param <S>          the type parameter
     * @param serviceClass the service class
     * @param headers      the headers
     * @return the s
     */
    public static <S extends BaseRetroClient> S createService(Class<S> serviceClass, final Map<String, String> headers) {
        if (clients.containsKey(serviceClass.getName())) {
            return serviceClass.cast(clients.get(serviceClass.getName()));
        }

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
        OkHttpClient httpClient = RetroHttpClient.getInstance().getOkHttpClient().newBuilder()
                .build();
        if (headers != null && !headers.isEmpty()) {
            httpClient.interceptors().add(new Interceptor() {
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

        Retrofit retrofit = builder.client(httpClient).build();
        S service = retrofit.create(serviceClass);
        clients.put(serviceClass.getName(), service);
        return service;
    }
}

