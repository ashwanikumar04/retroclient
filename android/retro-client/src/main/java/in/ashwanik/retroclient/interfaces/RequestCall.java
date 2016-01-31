package in.ashwanik.retroclient.interfaces;


/**
 * The interface Request call.
 *
 * @param <T> the type parameter
 */
public interface RequestCall<T> {
    /**
     * Cancel.
     */
    void cancel();

    /**
     * Enqueue.
     *
     * @param callback the callback
     */
    void enqueue(RequestCallback<T> callback);

    RequestCall<T> clone();

}
