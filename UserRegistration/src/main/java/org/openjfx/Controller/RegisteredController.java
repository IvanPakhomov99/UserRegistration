package org.openjfx.Controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.openjfx.App;
import org.openjfx.Entity.User;
import org.openjfx.Util.Singleton;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class RegisteredController implements Initializable {

    @FXML
    private Timeline timer;
    @FXML
    private Pane profilePane;
    @FXML
    private Label autoDisconnect;
    @FXML
    private Label username;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User user = Singleton.getInstance().getUser();
        username.setText("You're logged in like " + user.getUserName());
        autoDisconnect.setText("Auto disconnect in " + user.getAutoLogOut());
        AtomicInteger time = new AtomicInteger(user.getAutoLogOut());
        timer = new Timeline();
        timer.getKeyFrames().addAll(new KeyFrame(Duration.ZERO, e -> {
                    autoDisconnect.setText("Auto disconnect in " + time.toString());
                    time.addAndGet(-1);
                    if (time.intValue() < 0) {
                        logOut();
                        timer.stop();
                    }
                }),
                new KeyFrame(Duration.seconds(1))
        );
        timer.setCycleCount(Animation.INDEFINITE);
        timer.play();
    }

    private void logOut() {
        Stage newStage = new Stage();
        App app = new App();
        try {
            app.start(newStage);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        Stage thisStage = (Stage) profilePane.getScene().getWindow();
        thisStage.close();
    }

    public void logOutClick(ActionEvent event) {
        timer.stop();
        logOut();
    }
}
