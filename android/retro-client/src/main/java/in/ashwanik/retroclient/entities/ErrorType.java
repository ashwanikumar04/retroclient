package in.ashwanik.retroclient.entities;


/**
 * The enum Error type.
 */
public enum ErrorType {
    /**
     * SPECIFIC error type.
     */
    SPECIFIC(0),
    /**
     * GENERIC error type.
     */
    GENERIC(1),
    /**
     * Do not handle error type.
     */
    DO_NOT_HANDLE(2);
    private final int value;

     ErrorType(final int newValue) {
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