package in.ashwanik.retroclient.interfaces;

import android.app.Activity;


/**
 * The interface Service generator config.
 */
public interface ServiceGeneratorConfig {
    /**
     * Gets log category.
     *
     * @return the log category
     */
    String getLogCategory();

    /**
     * Is debug boolean.
     *
     * @return the boolean
     */
    boolean isDebug();

    /**
     * Gets logger.
     *
     * @return the logger
     */
    ILogger getLogger();

    /**
     * Gets current activity.
     *
     * @return the current activity
     */
    Activity getCurrentActivity();

    /**
     * Gets progress view color.
     *
     * @return the progress view color
     */
    int getProgressViewColor();
}
