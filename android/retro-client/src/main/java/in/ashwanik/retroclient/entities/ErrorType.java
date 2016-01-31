package in.ashwanik.retroclient.entities;


/**
 * The enum Error type.
 */
public enum ErrorType {
    /**
     * Specific error type.
     */
    Specific(0),
    /**
     * Generic error type.
     */
    Generic(1),
    /**
     * Do not handle error type.
     */
    DoNotHandle(2);
    private final int value;

    private ErrorType(final int newValue) {
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