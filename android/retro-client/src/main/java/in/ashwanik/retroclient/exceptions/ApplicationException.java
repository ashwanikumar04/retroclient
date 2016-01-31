package in.ashwanik.retroclient.exceptions;


import android.text.TextUtils;

import java.util.ArrayList;

public class ApplicationException extends Exception {
    private static final long serialVersionUID = 1L;
    private Exception exception;
    private String url;
    private ArrayList<String> params;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ArrayList<String> getParams() {
        return params;
    }

    public void setParams(ArrayList<String> params) {
        this.params = params;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    @Override
    public Throwable getCause() {
        return getException().getCause();
    }

    @Override
    public StackTraceElement[] getStackTrace() {
        return getException().getStackTrace();
    }

    @Override
    public String getMessage() {

        String localMessage = getException().toString();

        if (params != null) {
            localMessage = localMessage + "\n" + params.toString();
        }
        if (!TextUtils.isEmpty(getUrl())) {
            localMessage = localMessage + "\n" + getUrl();
        }

        if (!TextUtils.isEmpty(localMessage)) {
            localMessage = localMessage + "\n" + getException().getMessage();
            return localMessage;
        }
        return super.getMessage();
    }
}
