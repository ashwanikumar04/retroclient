package in.ashwanik.retroclient.entities;


/**
 * The enum Error Code.
 */
public enum ErrorCode {
    /**
     * Internet not available.
     */
    INTERNET_NOT_AVAILABLE(0),
    /**
     * Response not available.
     */
    RESPONSE_NOT_AVAILABLE(1);

    private final int value;

    ErrorCode(final int newValue) {
        value = newValue;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public int getValue() {
        return value;
    }
}