package mochi;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Mochi using FXML.
 */
public class Main extends Application {

    private Mochi mochi = new Mochi("./data/mochi.txt");

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);

            // Apply the CSS file
            scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());

            stage.setScene(scene);
            stage.setTitle("Mochi");
            stage.setResizable(true);
            fxmlLoader.<MainWindow>getController().setMochi(mochi);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
