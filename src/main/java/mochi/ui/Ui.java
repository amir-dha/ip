package mochi.ui;

/**
 * Handles all user interactions, including displaying messages and errors.
 */
public class Ui {
    private static final String LINE = " ----------------------------------------------------------------------------";
    private static final String BOT_NAME = "Mochi";

    /**
     * Prints a horizontal line for formatting purposes.
     */
    public void printLine() {
        System.out.println(LINE);
    }

    /**
     * Displays a welcome message when the application starts.
     */
    public void showWelcome() {
        printLine();
        System.out.println(" It's you again.. " + BOT_NAME + " at your service miserably.");
        System.out.println(" What you want?");
        printLine();
    }

    /**
     * Displays an error message when the task file cannot be loaded.
     */
    public void showLoadingError() {
        System.out.println("Bro cannot load tasks from file?!!");
    }

    /**
     * Displays a formatted error message.
     * @param message The error message to display.
     */
    public void showError(String message) {
        printLine();
        System.out.println(" " + message);
        printLine();
    }

    /**
     * Displays a general message to the user.
     * @param message The message to display.
     */
    public void showMessage(String message) {
        printLine();
        System.out.println(message);
        printLine();
    }
}
