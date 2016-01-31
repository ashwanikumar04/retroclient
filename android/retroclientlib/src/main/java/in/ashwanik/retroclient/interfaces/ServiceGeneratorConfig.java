package in.ashwanik.retroclient.interfaces;

import android.app.Activity;


/**
 * Created by AshwaniK on 1/31/2016.
 */
public interface ServiceGeneratorConfig {
    String getLogCategory();

    boolean isDebug();

    ILogger getLogger();

    Activity getCurrentActivity();

    int getProgressViewColor();
}
