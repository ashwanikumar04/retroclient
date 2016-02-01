package in.ashwanik.retroclientsample;

import android.app.Application;
import android.os.Build;

import in.ashwanik.retroclient.RetroClientServiceInitializer;
import in.ashwanik.retroclientsample.web.ApiUrls;


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
        RetroClientServiceInitializer.getInstance().initialize(ApiUrls.BASE_API_URL, getApplicationContext(), progressViewColor, true);
        RetroClientServiceInitializer.getInstance().setLogCategoryName("Retro-Client-Sample");

    }
}
