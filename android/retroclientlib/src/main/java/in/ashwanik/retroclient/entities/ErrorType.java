package in.ashwanik.retroclient.entities;


public enum ErrorType {
    Specific(0),
    Generic(1),
    DoNotHandle(2);
    private final int value;

    private ErrorType(final int newValue) {
        value = newValue;
    }

    public int getValue() {
        return value;
    }
}