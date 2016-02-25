package in.ashwanik.retroclient.entities;

/**
 * Created by ashwani on 25/2/16.
 */
public class DefaultData {

    private String noInternetErrorMessage;

    private int defaultResponseCode;
    private String genericErrorMessage;

    private DefaultData(Builder builder) {
        setNoInternetErrorMessage(builder.noInternetErrorMessage);
        setDefaultResponseCode(builder.defaultResponseCode);
        setGenericErrorMessage(builder.genericErrorMessage);
    }

    public String getNoInternetErrorMessage() {
        return noInternetErrorMessage;
    }

    public void setNoInternetErrorMessage(String noInternetErrorMessage) {
        this.noInternetErrorMessage = noInternetErrorMessage;
    }

    public int getDefaultResponseCode() {
        return defaultResponseCode;
    }

    public void setDefaultResponseCode(int defaultResponseCode) {
        this.defaultResponseCode = defaultResponseCode;
    }

    public String getGenericErrorMessage() {
        return genericErrorMessage;
    }

    public void setGenericErrorMessage(String genericErrorMessage) {
        this.genericErrorMessage = genericErrorMessage;
    }

    /**
     * {@code DefaultData} builder static inner class.
     */
    public static final class Builder {
        private String noInternetErrorMessage;
        private int defaultResponseCode;
        private String genericErrorMessage;

        public Builder() {
        }

        /**
         * Sets the {@code noInternetErrorMessage} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code noInternetErrorMessage} to set
         * @return a reference to this Builder
         */
        public Builder noInternetErrorMessage(String val) {
            noInternetErrorMessage = val;
            return this;
        }

        /**
         * Sets the {@code defaultResponseCode} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code defaultResponseCode} to set
         * @return a reference to this Builder
         */
        public Builder defaultResponseCode(int val) {
            defaultResponseCode = val;
            return this;
        }

        /**
         * Sets the {@code genericErrorMessage} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code genericErrorMessage} to set
         * @return a reference to this Builder
         */
        public Builder genericErrorMessage(String val) {
            genericErrorMessage = val;
            return this;
        }

        /**
         * Returns a {@code DefaultData} built from the parameters previously set.
         *
         * @return a {@code DefaultData} built with parameters of this {@code DefaultData.Builder}
         */
        public DefaultData build() {
            return new DefaultData(this);
        }
    }
}
