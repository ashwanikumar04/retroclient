package in.ashwanik.retroclient.interfaces;


import in.ashwanik.retroclient.entities.ErrorData;

/**
 * Created by AshwaniK on 1/31/2016.
 */
public interface RequestHandler<T> {

    void onSuccess(T response);

    void onError(ErrorData errorData);
}
