package in.ashwanik.retroclient.interfaces;


import in.ashwanik.retroclient.entities.ErrorData;


/**
 * This interface provides methods which are called in case of request completion.
 *
 * @param <T> the type parameter
 */
public interface RequestHandler<T> {

    /**
     * On success.
     *
     * @param response the response
     */
    void onSuccess(T response);

    /**
     * On error.
     *
     * @param errorData the error data
     */
    void onError(ErrorData errorData);
}
