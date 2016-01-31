package in.ashwanik.retroclient.interfaces;

import in.ashwanik.retroclient.entities.ErrorData;
import retrofit2.Response;

/**
 * Created by AshwaniK on 1/30/2016.
 */
public interface RequestCallback<T> {
    void onSuccess(Response<T> response);

    void onError(ErrorData errorData);
}