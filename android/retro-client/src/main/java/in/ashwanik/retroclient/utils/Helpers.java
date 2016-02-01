package in.ashwanik.retroclient.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by AshwaniK on 1/30/2016.
 */
public class Helpers {

    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    public static void d(String TAG, String message) {
        int maxLogSize = 1000;
        for (int index = 0; index <= message.length() / maxLogSize; index++) {
            int start = index * maxLogSize;
            int end = (index + 1) * maxLogSize;
            end = end > message.length() ? message.length() : end;
            Log.d(TAG, message.substring(start, end));
        }
    }
}
