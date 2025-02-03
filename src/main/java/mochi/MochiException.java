package mochi;

/**
 * Custom exception class for Mochi chatbot.
 */
public class MochiException extends Exception {

    /**
     * Constructs a MochiException with a specific error message.
     * @param message The error message.
     */
    public MochiException(String message) {
        super(message);
    }
}
