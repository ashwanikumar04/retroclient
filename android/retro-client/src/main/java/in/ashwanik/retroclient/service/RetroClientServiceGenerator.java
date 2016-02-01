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
 * The type Retro client service generator.
 */
public class RetroClientServiceGenerator {

    /**
     * The Headers.
     */
    Map<String, String> headers;
    /**
     * The Progress dialog.
     */
    TransparentProgressDialog progressDialog;
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
     * @param isSilent the is silent
     */
    public RetroClientServiceGenerator(Activity activity, boolean isSilent) {
        this(activity, isSilent, null);

    }

    /**
     * Instantiates a new Retro client service generator.
     *
     * @param config   the config
     * @param isSilent the is silent
     * @param headers  the headers
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
     * @param localContext  the local context
     * @param localIsSilent the local is silent
     */
    public RetroClientServiceGenerator(Context localContext,
                                       boolean localIsSilent) {
        this(localContext, localIsSilent, null);
    }

    /**
     * Instantiates a new Retro client service generator.
     *
     * @param localContext  the local context
     * @param localIsSilent the local is silent
     * @param headers       the headers
     */
    public RetroClientServiceGenerator(Context localContext,
                                       boolean localIsSilent,
                                       Map<String, String> headers) {
        context = localContext;
        this.headers = headers;
        isSilent = localIsSilent;
        logger = RetroClientServiceInitializer.getInstance().getLogger();
        logCategory = RetroClientServiceInitializer.getInstance().getLogCategoryName();
        isBaseActivity = false;
    }

    /**
     * Gets service.
     *
     * @param <T>          the type parameter
     * @param serviceClass the service class
     * @return the service
     */
    public <T> T getService(Class<T> serviceClass) {
        return ServiceGenerator.createService(serviceClass, headers);
    }

    /**
     * Gets service.
     *
     * @param <T>          the type parameter
     * @param serviceClass the service class
     * @param serviceName  the service name
     * @return the service
     */
    public <T> T getService(Class<T> serviceClass, String serviceName) {
        return ServiceGenerator.createService(serviceClass, headers, serviceName);
    }

    private void logOnLogCat(String message) {
        if (RetroClientServiceInitializer.getInstance().isDebug()) {
            Helpers.d(RetroClientServiceInitializer.getInstance().getLogCategoryName(), message);
        }
    }

    private void log(String message) {
        RetroClientServiceInitializer.getInstance().getLogger().log(message);
    }

    private void log(Exception exception) {
        RetroClientServiceInitializer.getInstance().getLogger().log(exception);
    }

    /**
     * Execute.
     *
     * @param <T>      the type parameter
     * @param call     the call
     * @param callback the callback
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
