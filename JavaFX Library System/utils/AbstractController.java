package utils;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class AbstractController {


    public void changeScene(ActionEvent event, String resource) throws IOException {
        Parent list = FXMLLoader.load(getClass().getResource(resource));
        Scene scene = new Scene(list);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("SignInPage");
        window.setScene(scene);
        window.show();
    }

    public void alert(String message, Alert.AlertType alertType){
        Alert alert = new Alert(alertType, message, ButtonType.OK);
        alert.showAndWait();
    }
}
