package mochi;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * Represents a dialog box consisting of an image and a text message.
 * The dialog box dynamically adjusts its alignment based on whether
 * it is from the user or the bot.
 */
public class DialogBox extends HBox {
    private Label dialog;
    private ImageView displayPicture;

    /**
     * Constructs a DialogBox with a label and an image.
     *
     * @param l  The label containing the message text.
     * @param iv The image representing the speaker.
     */
    public DialogBox(Label l, ImageView iv) {
        dialog = l;
        displayPicture = iv;

        dialog.setWrapText(true);
        dialog.setMaxWidth(Double.MAX_VALUE);
        dialog.setMinWidth(50);

        displayPicture.setFitWidth(50);
        displayPicture.setFitHeight(50);

        // Add CSS class
        this.getStyleClass().add("dialog-box");

        this.setSpacing(10);
        this.getChildren().addAll(displayPicture, dialog);

        HBox.setHgrow(dialog, Priority.ALWAYS);
        dialog.setMaxWidth(Double.MAX_VALUE);
    }

    public static DialogBox getUserDialog(String text, ImageView img) {
        Label userText = new Label(text);
        userText.setWrapText(true);
        userText.setAlignment(Pos.CENTER_RIGHT); // Align text to the right

        ImageView userImage = new ImageView(img.getImage());
        DialogBox db = new DialogBox(userText, userImage);
        db.setAlignment(Pos.CENTER_RIGHT);

        db.getStyleClass().addAll("dialog-box", "user-dialog");

        HBox.setHgrow(userText, Priority.ALWAYS);
        userText.setMaxWidth(Double.MAX_VALUE);

        userText.setStyle("-fx-padding: 10 20 10 40; -fx-text-alignment: right;");

        ObservableList<Node> children = FXCollections.observableArrayList(db.getChildren());
        FXCollections.reverse(children);
        db.getChildren().setAll(children);

        return db;
    }

    public static DialogBox getBotDialog(String text, ImageView img) {
        Label botText = new Label(text);
        botText.setWrapText(true);
        ImageView botImage = new ImageView(img.getImage());
        DialogBox db = new DialogBox(botText, botImage);
        db.setAlignment(Pos.CENTER_LEFT);

        db.getStyleClass().add("bot-dialog");

        // Ensure it stretches
        HBox.setHgrow(botText, Priority.ALWAYS);
        botText.setMaxWidth(Double.MAX_VALUE);

        return db;
    }
}
