package mochi.ui;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Handles all user interactions, including displaying messages and errors.
 */
public class Ui {
    private static final String BOT_NAME = "Mochi";


    /**
     * Displays a welcome message when the application starts.
     */
    public String showWelcome() {
        return "It's you again.. " + BOT_NAME + " at your service miserably.\nWhat you want?";
    }

    /**
     * Displays an error message when the task file cannot be loaded.
     */
    public String showLoadingError() {
        return "Bro cannot load tasks from file?!!";
    }

    /**
     * Displays a formatted error message.
     * @param message The error message to display.
     */
    public String showError(String message) {
        return "Got error: " + message;
    }

    /**
     * Displays an unexpected error message.
     * @param message The unexpected error message.
     */
    public String showUnexpectedError(String message) {
        return "Got unexpected error: " + message;
    }


    public static String showByeMessage() {
        return "Bye. Sayonara. Begone. Finally.";
    }

    /**
     * Displays a general message to the user.
     * @param message The message to display.
     */
    public String showMessage(String message) {
        return message;
    }

    /**
     * Delays the exit process for a few seconds before closing the application.
     */
    public void delayExit(Runnable exitAction) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                exitAction.run(); // Call the exit action (which closes the app)
            }
        }, 3000); // Delay for 3 seconds (3000ms)
    }
}
