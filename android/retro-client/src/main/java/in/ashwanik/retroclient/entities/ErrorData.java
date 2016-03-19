package in.ashwanik.retroclient.entities;


import in.ashwanik.retroclient.utils.Json;

/**
 * This class provides error messages and other information
 */
public class ErrorData {

    private ErrorType errorType;
    private String message;
    private int responseStatus;
    private String errorBody;
    private String actualException;
    private ErrorCode errorCode;

    private ErrorData(Builder builder) {
        setErrorType(builder.errorType);
        setMessage(builder.message);
        setResponseStatus(builder.responseStatus);
        setErrorBody(builder.errorBody);
        setActualException(builder.actualException);
        setErrorCode(builder.errorCode);
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public String getActualException() {
        return actualException;
    }

    public void setActualException(String actualException) {
        this.actualException = actualException;
    }

    public String getErrorBody() {
        return errorBody;
    }

    public void setErrorBody(String errorBody) {
        this.errorBody = errorBody;
    }


    /**
     * This method gives converted error details
     *
     * @param <T>    type of the error object
     * @param tClass Class of the error object
     * @return the typed object
     * @throws ClassNotFoundException the class not found exception
     */
    public <T> T getError(Class<T> tClass) throws ClassNotFoundException {
        if (errorBody == null || errorBody.isEmpty() || !Json.isValid(errorBody)) {
            return null;
        }
        return Json.deSerialize(errorBody, tClass);
    }

    /**
     * Gets error type.
     *
     * @return the error type
     */
    public ErrorType getErrorType() {
        return errorType;
    }

    private void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    private void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets response status.
     *
     * @return the response status
     */
    public int getResponseStatus() {
        return responseStatus;
    }

    private void setResponseStatus(int responseStatus) {
        this.responseStatus = responseStatus;
    }

    @Override
    public String toString() {
        return "ErrorData{" +
                "errorType=" + errorType +
                ", message='" + message + '\'' +
                ", responseStatus=" + responseStatus +
                '}';
    }


    /**
     * {@code ErrorData} builder static inner class.
     */
    public static final class Builder {
        private ErrorType errorType;
        private String message;
        private int responseStatus;
        private String errorBody;
        private String actualException;
        private ErrorCode errorCode;

        /**
         * Instantiates a new Builder.
         */
        public Builder() {
        }

        /**
         * Sets the {@code errorType} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code errorType} to set
         * @return a reference to this Builder
         */
        public Builder errorType(ErrorType val) {
            errorType = val;
            return this;
        }

        /**
         * Sets the {@code message} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code message} to set
         * @return a reference to this Builder
         */
        public Builder message(String val) {
            message = val;
            return this;
        }

        /**
         * Sets the {@code responseStatus} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code responseStatus} to set
         * @return a reference to this Builder
         */
        public Builder responseStatus(int val) {
            responseStatus = val;
            return this;
        }

        /**
         * Sets the {@code errorBody} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code errorBody} to set
         * @return a reference to this Builder
         */
        public Builder errorBody(String val) {
            errorBody = val;
            return this;
        }

        /**
         * Sets the {@code actualException} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code actualException} to set
         * @return a reference to this Builder
         */
        public Builder actualException(String val) {
            actualException = val;
            return this;
        }

        /**
         * Sets the {@code errorCode} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code errorCode} to set
         * @return a reference to this Builder
         */
        public Builder errorCode(ErrorCode val) {
            errorCode = val;
            return this;
        }

        /**
         * Returns a {@code ErrorData} built from the parameters previously set.
         *
         * @return a {@code ErrorData} built with parameters of this {@code ErrorData.Builder}
         */
        public ErrorData build() {
            return new ErrorData(this);
        }
    }
}
