package in.ashwanik.retroclientsample;

import android.app.Application;
import android.os.Build;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import in.ashwanik.retroclient.RetroClientServiceInitializer;
import in.ashwanik.retroclient.utils.Helpers;
import in.ashwanik.retroclientsample.web.ApiUrls;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        int progressViewColor;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            progressViewColor = getResources().getColor(R.color.colorPrimary, null);
        } else {
            progressViewColor = getResources().getColor(R.color.colorPrimary);
        }
        List<Interceptor> interceptors = new ArrayList<>();
        interceptors.add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response response = chain.proceed(request);
                Helpers.d(RetroClientServiceInitializer.getInstance().getLogCategoryName(), response.code() + "");
                return response;
            }
        });
        RetroClientServiceInitializer.getInstance()
                .initialize(ApiUrls.BASE_API_URL, progressViewColor, true, 10 * 1024 * 1024, getCacheDir(), interceptors, null);
        RetroClientServiceInitializer.getInstance().setLogCategoryName("Retro-Client-Sample");

    }
}
