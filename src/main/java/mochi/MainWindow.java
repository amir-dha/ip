package mochi;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import mochi.ui.Ui;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    @FXML
    private HBox headerBar;

    private Mochi mochi;
    private Ui ui = new Ui();

    private Image userImage = new Image(getClass().getResourceAsStream("/images/look.png"));
    private Image mochiImage = new Image(getClass().getResourceAsStream("/images/smile.png"));

    /**
     * Initializes the UI components and event listeners.
     * This method binds the scroll pane's vertical scrollbar to the height
     * of the dialog container so that it automatically scrolls down when new messages are added.
     * It also adds a key event listener to the user input field, allowing users to
     * submit input by pressing the Enter key.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());

        // Add key event listener for Enter key
        userInput.setOnKeyPressed(event -> {
            switch (event.getCode()) {
            case ENTER:
                handleUserInput();
                break;
            default:
                break;
            }
        });
    }


    /** Injects the Mochi instance */
    public void setMochi(Mochi m) {
        mochi = m;
        dialogContainer.getChildren().add(
                DialogBox.getBotDialog(ui.showWelcome(), new ImageView(mochiImage))
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Mochi's reply.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = mochi.getResponse(input);

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, new ImageView(userImage)),
                DialogBox.getBotDialog(response, new ImageView(mochiImage))
        );
        userInput.clear();
    }
}
