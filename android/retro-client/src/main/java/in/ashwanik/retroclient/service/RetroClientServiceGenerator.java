package in.ashwanik.retroclient.service;

import android.app.Activity;
import android.content.Context;

import java.util.Map;

import in.ashwanik.retroclient.RetroClientServiceInitializer;
import in.ashwanik.retroclient.entities.ErrorData;
import in.ashwanik.retroclient.entities.ErrorType;
import in.ashwanik.retroclient.interfaces.ILogger;
import in.ashwanik.retroclient.interfaces.RequestHandler;
import in.ashwanik.retroclient.ui.TransparentProgressDialog;
import in.ashwanik.retroclient.utils.Helpers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * This class generates clients for making network calls
 */
public class RetroClientServiceGenerator {
    private Map<String, String> headers;
    private TransparentProgressDialog progressDialog;
    private boolean isBaseActivity;
    private ILogger logger;
    private boolean isSilent;
    private Context context;
    private String logCategory;
    private int progressViewColor;

    /**
     * Instantiates a new Retro client service generator.
     *
     * @param activity the activity
     * @param isSilent Flag to make a network call without progress view
     */
    public RetroClientServiceGenerator(Activity activity, boolean isSilent) {
        this(activity, isSilent, null);

    }

    /**
     * Instantiates a new Retro client service generator.
     *
     * @param config   config
     * @param isSilent Flag to make a network call without progress view
     * @param headers  map of headers
     */
    public RetroClientServiceGenerator(Activity config, boolean isSilent, Map<String, String> headers) {
        context = config;
        this.headers = headers;
        this.isSilent = isSilent;
        this.progressViewColor = RetroClientServiceInitializer.getInstance().getProgressViewColor();
        logger = RetroClientServiceInitializer.getInstance().getLogger();
        logCategory = RetroClientServiceInitializer.getInstance().getLogCategoryName();
        isBaseActivity = true;
    }

    /**
     * Instantiates a new Retro client service generator.
     *
     * @param context  context
     * @param isSilent Flag to make a network call without progress view
     */
    public RetroClientServiceGenerator(Context context,
                                       boolean isSilent) {
        this(context, isSilent, null);
    }

    /**
     * Instantiates a new Retro client service generator.
     *
     * @param context  context
     * @param isSilent Flag to make a network call without progress view
     * @param headers  headers
     */
    public RetroClientServiceGenerator(Context context,
                                       boolean isSilent,
                                       Map<String, String> headers) {
        this.context = context;
        this.headers = headers;
        this.isSilent = isSilent;
        logger = RetroClientServiceInitializer.getInstance().getLogger();
        logCategory = RetroClientServiceInitializer.getInstance().getLogCategoryName();
        isBaseActivity = false;
    }

    /**
     * Gets service.
     *
     * @param <T>          Type of the Client to generate
     * @param serviceClass Class of the client to generate
     * @return the service
     */
    public <T> T getService(Class<T> serviceClass) {
        return ServiceGenerator.createService(serviceClass, headers);
    }

    /**
     * Gets service.
     *
     * @param <T>          Type of the Client to generate
     * @param serviceClass Class of the client to generate
     * @param serviceName  Name of the service in case same type of client is required with different options
     * @return the service
     */
    public <T> T getService(Class<T> serviceClass, String serviceName) {
        return ServiceGenerator.createService(serviceClass, headers, serviceName);
    }

    private void logOnLogCat(String message) {
        if (RetroClientServiceInitializer.getInstance().isDebug()) {
            Helpers.d(logCategory, message);
        }
    }

    private void log(String message) {
        logger.log(message);
    }

    private void log(Exception exception) {
        logger.log(exception);
    }

    /**
     * Execute the network call
     *
     * @param <T>      Type of the response data
     * @param call     Retrofit call object
     * @param callback Handler for the request
     */
    public <T> void execute(Call<T> call, final RequestHandler<T> callback) {
        boolean isNetAvailable = Helpers.isOnline(context);
        if (isNetAvailable) {
            if (!isSilent) {
                progressDialog = new TransparentProgressDialog(context, progressViewColor);
                progressDialog.setCancelable(false);
                dismissProgressDialog();
                progressDialog.show();
            }
            call.enqueue(new Callback<T>() {
                @Override
                public void onResponse(Response<T> response) {
                    if (isBaseActivity && ((Activity) context).isFinishing()) {
                        return;
                    }
                    try {
                        dismissProgressDialog();
                        if (response != null) {
                            int code = response.code();
                            if (response.isSuccess() && code >= 200 && code < 300) {
                                callback.onSuccess(response.body());
                            } else {
                                if (response.errorBody() != null) {
                                    callback.onError(new ErrorData.Builder().responseStatus(code).errorType(ErrorType.Specific).message("Some error occurred").errorBody(response.errorBody().string()).build());
                                } else {
                                    callback.onError(new ErrorData.Builder().responseStatus(code).errorType(ErrorType.Generic).message("Some error occurred").build());
                                }
                            }
                        } else {
                            callback.onError(new ErrorData.Builder().responseStatus(0).errorType(ErrorType.Generic).message("Some error occurred").build());
                        }

                    } catch (Exception exception) {
                        log(exception);
                        callback.onError(new ErrorData.Builder().responseStatus(0).errorType(ErrorType.Generic).message("Some error occurred").build());
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    if (isBaseActivity && ((Activity) context).isFinishing()) {
                        return;
                    }
                    try {
                        dismissProgressDialog();
                    } finally {
                        callback.onError(new ErrorData.Builder().responseStatus(0).errorType(ErrorType.Generic).message("Some error occurred").build());
                    }
                }
            });
        } else {
            logOnLogCat("Network not available");
            callback.onError(new ErrorData.Builder().responseStatus(0).errorType(isSilent ? ErrorType.DoNotHandle : ErrorType.Specific).message("Please check network connection!!!").build());
        }
    }

    /**
     * Dismiss progress dialog.
     */
    public void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

}
