package in.ashwanik.retroclient.interfaces;

/**
 * The interface Logger.
 */
public interface ILogger {
    /**
     * Log.
     *
     * @param message the message
     */
    void log(String message);

    /**
     * Log.
     *
     * @param exception the exception
     */
    void log(Exception exception);
}
