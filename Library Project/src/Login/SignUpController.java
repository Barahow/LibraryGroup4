package Login;

import DataBase.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import utils.AbstractController;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignUpController extends AbstractController implements Initializable {
    @FXML
    private TextField txtname;

    @FXML
    private TextField txtemail;

    @FXML
    private TextField txtaddress;

    @FXML
    private TextField txtusername;

    @FXML
    private PasswordField txtpassword;

    @FXML
    private PasswordField txtconfirm;

    @FXML
    private Button btnsignup;


    @FXML
    public void register(ActionEvent event) {
        String SSN = txtname.getText();
        String name = txtemail.getText();
        String address = txtaddress.getText();
        String phone_number = txtusername.getText();
        String email = txtpassword.getText();
        String membertype = txtconfirm.getText();
        String confirm = txtconfirm.getText();


        if (name.equals("")) {
            alert("Name cannot be empty", Alert.AlertType.ERROR);
            return;
        }

        if (email.equals("")) {
            alert("Email cannot be empty", Alert.AlertType.ERROR);
            return;
        }
        if (address.equals("")) {
            alert("Address cannot be empty", Alert.AlertType.ERROR);
            return;
        }
        if (SSN.equals("")) {
            alert("username cannot be empty", Alert.AlertType.ERROR);
            return;
        }
        if (membertype.equals("")) {
            alert("password cannot be empty", Alert.AlertType.ERROR);
            return;
        }
        if (confirm.equals("")) {
            alert("confirm password cannot be empty", Alert.AlertType.ERROR);
            return;
        }


        /*if (!password.equals(confirm)) {
            alert("Wrong password", Alert.AlertType.ERROR);
            return;
        }*/


        DBConnection connection = new DBConnection();
        connection.signUp(SSN,name, address, phone_number,email,membertype);
        alert("User registered successfully", Alert.AlertType.CONFIRMATION);
        try {
            // change scene
            changeScene(event, "../Home/home.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
