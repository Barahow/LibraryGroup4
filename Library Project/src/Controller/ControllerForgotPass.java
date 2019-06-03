package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Book;
import model.BorrowBook;
import model.MailSender;
import model.ProjectManager;
import sample.DBConnection;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;


public class ControllerForgotPass implements Initializable {
    @FXML
    private TextField emailField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void sendPass(ActionEvent event){
        String email = emailField.getText(); //TODO check that user is registered in db
        DBConnection db = new DBConnection();
        String[] info = db.passwordQuery(email);
        String name = info[1];
        String pass = info[0];

        if(name == null && pass == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Incorrect email", ButtonType.OK);
            alert.show();
            return;
        }

        MailSender.sendForgottenPassword(email, name, pass);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Password sent", ButtonType.OK);
        alert.showAndWait();

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();
    }
}
