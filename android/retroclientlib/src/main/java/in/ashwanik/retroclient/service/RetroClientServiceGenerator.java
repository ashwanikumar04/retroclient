package in.ashwanik.retroclient.service;

import android.app.Activity;
import android.content.Context;

import java.util.Map;

import in.ashwanik.retroclient.entities.ErrorData;
import in.ashwanik.retroclient.entities.ErrorType;
import in.ashwanik.retroclient.interfaces.ILogger;
import in.ashwanik.retroclient.interfaces.RequestCall;
import in.ashwanik.retroclient.interfaces.RequestCallback;
import in.ashwanik.retroclient.interfaces.RequestHandler;
import in.ashwanik.retroclient.interfaces.ServiceGeneratorConfig;
import in.ashwanik.retroclient.ui.TransparentProgressDialog;
import in.ashwanik.retroclient.utils.Helpers;
import retrofit2.Response;

/**
 * Created by AshwaniK on 1/31/2016.
 */
public class RetroClientServiceGenerator {

    Map<String, String> headers;
    TransparentProgressDialog progressDialog;
    private boolean isBaseActivity;
    private ILogger logger;
    private boolean isSilent;
    private Context context;
    private boolean isDebug;
    private String logCategory;
    private int progressViewColor;

    public RetroClientServiceGenerator(ServiceGeneratorConfig config, boolean isSilent) {
        this(config, isSilent, null);

    }

    public RetroClientServiceGenerator(ServiceGeneratorConfig config, boolean isSilent, Map<String, String> headers) {
        context = config.getCurrentActivity();
        this.headers = headers;
        this.isSilent = isSilent;
        this.progressViewColor = config.getProgressViewColor();
        logger = config.getLogger();
        isDebug = config.isDebug();
        logCategory = config.getLogCategory();
        isBaseActivity = true;
    }

    public RetroClientServiceGenerator(Context localContext,
                                       boolean localIsSilent,
                                       ILogger iLogger,
                                       boolean localIsDebug,
                                       String localLogCategory) {
        this(localContext, localIsSilent, iLogger, localIsDebug, localLogCategory, null);
    }

    public RetroClientServiceGenerator(Context localContext,
                                       boolean localIsSilent,
                                       ILogger iLogger,
                                       boolean localIsDebug,
                                       String localLogCategory, Map<String, String> headers) {
        context = localContext;
        this.headers = headers;
        isSilent = localIsSilent;
        logger = iLogger;
        isDebug = localIsDebug;
        isBaseActivity = false;
        logCategory = localLogCategory;
    }

    public <T> T getService(Class<T> serviceClass) {
        return ServiceGenerator.createService(serviceClass, headers, isDebug);
    }

    public <T> void execute(RequestCall<T> call, final RequestHandler<T> callback) {
        boolean isNetAvailable = Helpers.isOnline(context);
        if (isNetAvailable) {
            if (!isSilent) {
                progressDialog = new TransparentProgressDialog(context, progressViewColor);
                progressDialog.setCancelable(false);
                dismissProgressDialog();
                progressDialog.show();
            }
            call.enqueue(new RequestCallback<T>() {
                @Override
                public void onSuccess(Response<T> response) {
                    if (isBaseActivity && ((Activity) context).isFinishing()) {
                        return;
                    }
                    dismissProgressDialog();
                    callback.onSuccess(response.body());
                }

                @Override
                public void onError(ErrorData errorData) {
                    if (isBaseActivity && ((Activity) context).isFinishing()) {
                        return;
                    }
                    dismissProgressDialog();
                    callback.onError(errorData);
                }
            });
        } else {
            callback.onError(new ErrorData.Builder().responseStatus(0).errorType(isSilent ? ErrorType.DoNotHandle : ErrorType.Specific).message("Please check network connection!!!").build());
        }
    }

    public void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

}
