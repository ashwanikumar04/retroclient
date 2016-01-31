package in.ashwanik.retroclient.interfaces;

import in.ashwanik.retroclient.entities.ErrorData;
import retrofit2.Response;


/**
 * The interface Request callback.
 *
 * @param <T> the type parameter
 */
public interface RequestCallback<T> {
    /**
     * On success.
     *
     * @param response the response
     */
    void onSuccess(Response<T> response);

    /**
     * On error.
     *
     * @param errorData the error data
     */
    void onError(ErrorData errorData);
}