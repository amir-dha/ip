package mochi;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

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

    private Mochi mochi;

    private Image userImage = new Image(getClass().getResourceAsStream("/images/look.png"));
    private Image mochiImage = new Image(getClass().getResourceAsStream("/images/smile.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Mochi instance */
    public void setMochi(Mochi m) {
        mochi = m;
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

