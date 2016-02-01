package in.ashwanik.retroclient.interfaces;

/**
 * This interface provides methods for logging
 */
public interface ILogger {
    /**
     * Log string message
     *
     * @param message the message
     */
    void log(String message);

    /**
     * Log exception
     *
     * @param exception the exception
     */
    void log(Exception exception);
}
