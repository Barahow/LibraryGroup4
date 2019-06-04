package Controller;

import model.Account;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import sample.DBConnection;
import sample.Main;
import sample.SceneData;

import java.util.ArrayList;

public class ControllerLogIn {

    @FXML
    private TextField txtfieldemail;

    @FXML
    private PasswordField txtpassword;


    @FXML
    private Label lblstatus;
    @FXML
    private TextField Email;


    DBConnection dbhanlder;



    public void initialize() {

        lblstatus.setText("Status: Waiting for login");


    }

    public void Onkeypressed(KeyEvent keyevent) {
        if (keyevent.getCode().equals(KeyCode.ENTER)) {
            tryLogin(txtfieldemail.getText(),txtpassword.getText());
        }
    }

    public void btnLoginPressed(ActionEvent event) {
        tryLogin(txtfieldemail.getText(),txtpassword.getText());
    }

    private void tryLogin(String logInEmail, String loginPassword) {

        ArrayList<Account> member = DBConnection.getInstance().getAccounts(logInEmail,loginPassword);

        if (member.size() == 0) {


            lblstatus.setText("Status: No member with Email " + logInEmail +" and " + loginPassword+ " Please try again");
            return;
        }

        Account loginMember = member.get(0);

        SceneData.getInstance().setLoggedInCustomer(loginMember);



        Main.loadLibraryView();

    }

    public void registerButtonPressed() {
        Main.LoadRegisterView();
    }
}
