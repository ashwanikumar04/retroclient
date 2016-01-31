package in.ashwanik.retroclient.entities;


public class ErrorData {

    private ErrorType errorType;
    private String message;
    private int responseStatus;

    private ErrorData(Builder builder) {
        setErrorType(builder.errorType);
        setMessage(builder.message);
        setResponseStatus(builder.responseStatus);
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    private void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }

    public String getMessage() {
        return message;
    }

    private void setMessage(String message) {
        this.message = message;
    }

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
         * Returns a {@code ErrorData} built from the parameters previously set.
         *
         * @return a {@code ErrorData} built with parameters of this {@code ErrorData.Builder}
         */
        public ErrorData build() {
            return new ErrorData(this);
        }
    }
}
