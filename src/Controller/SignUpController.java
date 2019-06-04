package Controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.AbstractController;
import sample.DBConnection;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController extends AbstractController implements Initializable {
    @FXML
    private TextField txtname;

    @FXML
    private TextField txtssn;

    @FXML
    private TextField txtemail;

    @FXML
    private TextField txtaddress;

    @FXML
    private TextField txtphonenumber;

    @FXML
    private PasswordField txtpassword;

    @FXML
    private PasswordField txtconfirm;

    @FXML
    private Button btnsignup;


    @FXML
    public void register(ActionEvent event) {
        String name = txtname.getText();
        String ssn = txtssn.getText();
        String email = txtemail.getText();
        String address = txtaddress.getText();
        String phonenumber = txtphonenumber.getText();
        String password = txtpassword.getText();
        String confirm = txtconfirm.getText();


        if (name.equals("")) {
            alert("Name cannot be empty", Alert.AlertType.ERROR);
            return;
        }
        if (ssn.equals("")) {
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
        if (phonenumber.equals("")) {
            alert("Phone number cannot be empty", Alert.AlertType.ERROR);
            return;
        }
        if (password.equals("")) {
            alert("password cannot be empty", Alert.AlertType.ERROR);
            return;
        }
        if (confirm.equals("")) {
            alert("confirm password cannot be empty", Alert.AlertType.ERROR);
            return;
        }


        if (!password.equals(confirm)) {
            alert("Wrong password", Alert.AlertType.ERROR);
            return;
        }


        DBConnection connection = new DBConnection();
        connection.signUp(ssn, name, address, phonenumber, email, password, 0);
        alert("User registered successfully", Alert.AlertType.CONFIRMATION);

        // change scene
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
