package mochi;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * Represents a dialog box consisting of an image and a text message.
 * The dialog box dynamically adjusts its alignment based on whether
 * it is from the user or the bot.
 */
public class DialogBox extends HBox {
    private Label dialog;
    private ImageView displayPicture;

    /**
     * Constructs a `DialogBox` with a label and an image.
     *
     * @param l  The label containing the message text.
     * @param iv The image representing the speaker.
     */
    public DialogBox(Label l, ImageView iv) {
        dialog = l;
        displayPicture = iv;

        dialog.setWrapText(true);
        displayPicture.setFitWidth(50);
        displayPicture.setFitHeight(50);

        // Ensure text container dynamically resizes
        dialog.setMaxWidth(250);
        dialog.setMinHeight(Region.USE_PREF_SIZE);

        dialog.setStyle("-fx-padding: 10; -fx-background-color: #FFFFCC; -fx-background-radius: 15; "
                + "-fx-border-radius: 15; -fx-border-color: #A28BE2; -fx-font-size: 14px; "
                + "-fx-text-fill: #000000;");

        this.setSpacing(10);
        this.getChildren().addAll(displayPicture, dialog);
    }

    /**
     * Creates a dialog box for user messages, aligning text to the right.
     *
     * @param text The text message from the user.
     * @param img  The image representing the user.
     * @return A `DialogBox` instance formatted as a user message.
     */
    public static DialogBox getUserDialog(String text, ImageView img) {
        Label userText = new Label(text);
        userText.setWrapText(true);
        ImageView userImage = new ImageView(img.getImage());
        DialogBox db = new DialogBox(userText, userImage);
        db.setAlignment(Pos.CENTER_RIGHT);
        userText.setStyle("-fx-padding: 10; -fx-background-color: #FFD1DC; "
                        + "-fx-background-radius: 15; -fx-border-radius: 15; -fx-border-color: #C79BD3; "
                        + "-fx-font-size: 14px; -fx-text-fill: #000000;");
        db.setStyle("-fx-background-color: transparent");

        // Reverse order to push text to the left
        ObservableList<Node> children = FXCollections.observableArrayList(db.getChildren());
        FXCollections.reverse(children);
        db.getChildren().setAll(children);

        return db;
    }



    /**
     * Creates a dialog box for bot responses, aligning text to the left.
     *
     * @param text The text message from the bot.
     * @param img  The image representing the bot.
     * @return A `DialogBox` instance formatted as a bot response.
     */
    public static DialogBox getBotDialog(String text, ImageView img) {
        Label botText = new Label(text);
        botText.setStyle("-fx-padding: 10; -fx-font-size: 14px; -fx-text-fill: #000000;");

        ImageView botImage = new ImageView(img.getImage());

        DialogBox db = new DialogBox(botText, botImage);
        db.setAlignment(Pos.CENTER_LEFT);

        db.setStyle("-fx-background-color: transparent;");

        return db;
    }

}
