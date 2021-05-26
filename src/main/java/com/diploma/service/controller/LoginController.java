package com.diploma.service.controller;

import com.diploma.enums.UserRole;
import com.diploma.persistence.dao.UserDao;
import com.diploma.persistence.entity.UserEntity;
import com.diploma.service.Toast;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;

@Component
public class LoginController {

    public PasswordField pin_field;
    public Button pin_1_btn;
    public Button pin_2_btn;
    public Button pin_3_btn;
    public Button pin_4_btn;
    public Button pin_5_btn;
    public Button pin_6_btn;
    public Button pin_7_btn;
    public Button pin_8_btn;
    public Button pin_9_btn;
    public Button pin_0_btn;
    public Button pin_ok_btn;
    public Button pin_back_btn;

    private final UserDao userDao;
    private final Resource adminScene;
    private final Resource userScene;
    private final ApplicationContext applicationContext;

    public static UserEntity loggedUser;

    public LoginController(UserDao userDao,
                           @Value("classpath:/scenes/admin_menu.fxml") Resource adminScene,
                           @Value("classpath:/scenes/sale_scene.fxml") Resource userScene,
                           ApplicationContext applicationContext) {
        this.userDao = userDao;
        this.adminScene = adminScene;
        this.userScene = userScene;
        this.applicationContext = applicationContext;
    }

    public static UserEntity getLoggedUser() {
        return loggedUser;
    }

    @FXML
    public void initialize() {

        this.pin_0_btn.setOnAction(pinBtnHandler);
        this.pin_1_btn.setOnAction(pinBtnHandler);
        this.pin_2_btn.setOnAction(pinBtnHandler);
        this.pin_3_btn.setOnAction(pinBtnHandler);
        this.pin_4_btn.setOnAction(pinBtnHandler);
        this.pin_5_btn.setOnAction(pinBtnHandler);
        this.pin_6_btn.setOnAction(pinBtnHandler);
        this.pin_7_btn.setOnAction(pinBtnHandler);
        this.pin_8_btn.setOnAction(pinBtnHandler);
        this.pin_9_btn.setOnAction(pinBtnHandler);
        this.pin_back_btn.setOnAction(actionEvent -> {
            if (!pin_field.getText().isEmpty())
                pin_field.deleteText(pin_field.getText().length() - 1, pin_field.getText().length());
        });
        this.pin_ok_btn.setOnAction(actionEvent -> {
            UserEntity userEntity = userDao.findOneByPassword(pin_field.getText());
            if (userEntity == null) {
                String toastMsg = "Wrong password!";
                int toastMsgTime = 1500;
                int fadeInTime = 300;
                int fadeOutTime = 300;
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Toast.makeText(stage, toastMsg, toastMsgTime, fadeInTime, fadeOutTime);
            } else {
                loggedUser = userEntity;
                if (userEntity.getUserRoleEntity().getRole().equals(UserRole.ADMIN)) {
                    changeScene(adminScene);
                } else {
                    changeScene(userScene);
                }
            }
        });
    }

    private void changeScene(Resource resource) {
        try {
            Stage stage = (Stage) pin_ok_btn.getScene().getWindow();
            URL url = resource.getURL();
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private final EventHandler<ActionEvent> pinBtnHandler = event -> {

        Button source = (Button) event.getSource();
        String text = source.getText();
        pin_field.appendText(text);
    };

}
