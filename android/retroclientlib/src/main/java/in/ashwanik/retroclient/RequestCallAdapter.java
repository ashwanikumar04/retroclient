package in.ashwanik.retroclient;

import java.io.IOException;

import in.ashwanik.retroclient.entities.ErrorData;
import in.ashwanik.retroclient.entities.ErrorType;
import in.ashwanik.retroclient.interfaces.RequestCall;
import in.ashwanik.retroclient.interfaces.RequestCallback;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RequestCallAdapter<T> implements RequestCall<T> {
    private final Call<T> call;

    RequestCallAdapter(Call<T> call) {
        this.call = call;
    }

    @Override
    public void cancel() {
        call.cancel();
    }

    @Override
    public void enqueue(final RequestCallback<T> callback) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Response<T> response) {
                if (response != null && response.isSuccess()) {
                    int code = response.code();
                    if (code >= 200 && code < 300) {
                        callback.onSuccess(response);
                    } else {
                        callback.onError(new ErrorData.Builder().responseStatus(code).errorType(ErrorType.Generic).message("Some error occurred").build());
                    }
                } else {
                    if (response != null && response.errorBody() != null) {
                        try {
                            callback.onError(new ErrorData.Builder().responseStatus(0).errorType(ErrorType.Generic).message(response.errorBody().string()).build());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        callback.onError(new ErrorData.Builder().responseStatus(0).errorType(ErrorType.Generic).message("Some error occurred").build());
                    }
                }
            }

            @Override
            public void onFailure(Throwable e) {
                callback.onError(new ErrorData.Builder().responseStatus(0).errorType(ErrorType.Generic).message("Some error occurred").build());
            }
        });
    }

    @Override
    public RequestCall<T> clone() {
        return new RequestCallAdapter<>(call.clone());
    }
}