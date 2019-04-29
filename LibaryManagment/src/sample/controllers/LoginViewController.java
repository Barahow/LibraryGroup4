package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginViewController implements Initializable {
    private final String testEmail = "test@test.com";
    private final String testPassword = "test";
    @FXML
    TextField emailTextField;
    @FXML
    PasswordField passwordTextField;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void pressButton(ActionEvent event) {
        String value = ((Button) event.getSource()).getText();

        switch (value) {
            case "Log in":
                if (userAuthentication(emailTextField.getText(), passwordTextField.getText())) {
                    changeScene(event, "../views/mainView.fxml");
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Incorrect login,", ButtonType.OK);
                    alert.showAndWait();
                    return;
                }

                //changeScene(event, "../views/mainView.fxml");
                break;
        }

    }

    private void changeScene(javafx.event.ActionEvent event, String resource) {
        try {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));

            Parent root = loader.load();
            //   ((MainViewController) loader.getController()).setItems(items);
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean userAuthentication(String email, String password) {
        if (email.equals(testEmail) && password.equals(testPassword)) {
            return true;
        } else {
            return false;
        }
    }
}
