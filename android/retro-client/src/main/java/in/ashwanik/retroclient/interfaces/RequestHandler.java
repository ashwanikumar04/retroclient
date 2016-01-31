package in.ashwanik.retroclient.interfaces;


import in.ashwanik.retroclient.entities.ErrorData;


/**
 * The interface Request handler.
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
