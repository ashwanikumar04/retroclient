package in.ashwanik.retroclient.interfaces;

public interface ILogger {
    void log(String message);

    void log(Exception exception);
}
