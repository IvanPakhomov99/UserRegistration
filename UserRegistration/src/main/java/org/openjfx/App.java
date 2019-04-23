package org.openjfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/sample.fxml"));
        Parent root = loader.load();
        setStage(primaryStage, "UserRegistration", root);
    }

    public void loadNewWindow(Stage childStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/profile.fxml"));
        Parent root = loader.load();
        setStage(childStage, "Profile", root);
    }

    private void setStage(Stage stage, String name, Parent root) {
        stage.setTitle(name);
        stage.setScene(new Scene(root, 491, 855));
        stage.setResizable(false);
        stage.show();
    }
}