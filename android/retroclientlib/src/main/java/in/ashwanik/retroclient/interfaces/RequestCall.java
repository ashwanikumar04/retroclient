package in.ashwanik.retroclient.interfaces;

/**
 * Created by AshwaniK on 1/30/2016.
 */
public interface RequestCall<T> {
    void cancel();

    void enqueue(RequestCallback<T> callback);

    RequestCall<T> clone();

}
