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

import java.io.IOException;
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
        if (ssn.matches("^\\d{6}(\\d{2})?[-+]\\d{4}$")) {
            alert("need to follow this format yyyy-mm-dd-xxxx", Alert.AlertType.ERROR);
        }
        if (email.equals("")) {
            alert("Email cannot be empty", Alert.AlertType.ERROR);
            return;
        }
        if (address.equals("")) {
            alert("Address cannot be empty", Alert.AlertType.ERROR);
            return;
        }
        if (phonenumber.matches("\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}")) {
            alert("Phone number needs to be 10 digits ", Alert.AlertType.ERROR);
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
        connection.signUp(ssn, name, address, phonenumber, email, password);
        alert("User registered successfully", Alert.AlertType.CONFIRMATION);
        try {
            // change scene
            changeScene(event, "/view/StartMenu.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
