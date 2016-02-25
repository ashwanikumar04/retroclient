package in.ashwanik.retroclient.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;


public class Helpers {

    /**
     * Method to check if network is available.
     *
     * @param context the context
     * @return Flag indicating if Network is available or not
     */
    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    /**
     * Method to trim log messages for debug
     *
     * @param TAG     Tag for the message
     * @param message Message to log
     */
    public static void d(String TAG, String message) {
        int maxLogSize = 1000;
        for (int index = 0; index <= message.length() / maxLogSize; index++) {
            int start = index * maxLogSize;
            int end = (index + 1) * maxLogSize;
            end = end > message.length() ? message.length() : end;
            Log.d(TAG, message.substring(start, end));
        }
    }

    /**
     * Method to trim log messages for error
     *
     * @param TAG     Tag for the message
     * @param message Message to log
     */
    public static void e(String TAG, String message) {
        int maxLogSize = 1000;
        for (int index = 0; index <= message.length() / maxLogSize; index++) {
            int start = index * maxLogSize;
            int end = (index + 1) * maxLogSize;
            end = end > message.length() ? message.length() : end;
            Log.e(TAG, message.substring(start, end));
        }
    }

    /**
     * This return exception as string.
     *
     * @param throwable Throwable
     * @return exception string
     */
    public static String exceptionToString(Throwable throwable) {
        StringWriter errors = new StringWriter();
        throwable.printStackTrace(new PrintWriter(errors));
        return errors.toString();
    }
}
