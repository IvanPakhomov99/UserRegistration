package org.openjfx.Controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.openjfx.App;
import org.openjfx.Entity.User;
import org.openjfx.Repository.UserDAO;
import org.openjfx.Util.ShakeTransition;
import org.openjfx.Util.Singleton;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


//TODO: валидация данных 1.5 часа
//TODO: почистить код 2 часа

public class Controller implements Initializable {
    private static final Logger LOGGER = Logger.getLogger(Controller.class.getName());
    private UserDAO userDAO;

    @FXML
    private Pane pane;
    @FXML
    private Label period;
    @FXML
    private Button logIn;
    @FXML
    private Label minute;
    @FXML
    private Label hour;
    @FXML
    private Label second;
    @FXML
    private PasswordField password;


    @FXML
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userDAO = new UserDAO();
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss a");
            String[] result = simpleDateFormat.format(date).split("\\s|:");
            hour.setText(result[0] + " :");
            minute.setText(" " + result[1] + " :");
            second.setText(" " + result[2]);
            period.setText(" " + result[3]);
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    @FXML
    public void handleButtonClick(ActionEvent actionEvent) throws IOException {
        Button button = (Button) actionEvent.getSource();
        button.arm();
        PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
        pause.setOnFinished(event -> {
            button.disarm();
            button.fire();
        });
        pause.play();
        pause.pause();
        String result = button.getText();
        password.setText(password.getText() + result);
    }

    @FXML
    public void handleLogInClick(ActionEvent actionEvent) throws Exception {
        String result = password.getText();
        User user = null;
        try {
            user = userDAO.getUser(result);
        } catch (NumberFormatException e) {
            LOGGER.log(Level.SEVERE, "Field password's required");
        } finally {
            password.setText("");
            if (user != null) {
                Singleton.getInstance().setUser(user);
                Stage newStage = new Stage();
                App app = new App();
                app.loadNewWindow(newStage);
                Stage thisStage = (Stage) logIn.getScene().getWindow();
                thisStage.close();
            } else {
                LOGGER.log(Level.SEVERE, "Wrong password");
                ShakeTransition anim = new ShakeTransition(pane, actionEvent);
                anim.playFromStart();
            }
        }
    }

    public void clearPassword(MouseEvent actionEvent) {
        password.setText("");
    }

    public void backspacePassword(ActionEvent actionEvent) {
        String string = password.getText();
        if (!string.equals("")) {
            password.setText(string.substring(0, string.length() - 1));
        } else {
            LOGGER.log(Level.SEVERE, "Passwords lengths is 0");
        }
    }
}