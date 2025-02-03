package mochi.ui;

public class Ui {
    private static final String LINE = " ----------------------------------------------------------------------------";
    private static final String BOT_NAME = "Mochi";

    public void printLine() {
        System.out.println(LINE);
    }
    public void showWelcome() {
        printLine();
        System.out.println(" It's you again.. " + BOT_NAME + " at your service miserably.");
        System.out.println(" What you want?");
        printLine();
    }

    public void showLoadingError() {
        System.out.println("Bro cannot load tasks from file?!!");
    }

    public void showError(String message) {
        printLine();
        System.out.println(" " + message);
        printLine();
    }

    public void showMessage(String message) {
        printLine();
        System.out.println(message);
        printLine();
    }
}
