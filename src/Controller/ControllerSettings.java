package Controller;

import model.Account;
import model.Member;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import sample.DBConnection;
import sample.SceneData;

import java.util.ArrayList;

public class ControllerSettings {
    @FXML
    private AnchorPane rootpane;
    @FXML
    private TextField txtssn;
    @FXML
    private TextField txtemail;
    @FXML
    private PasswordField txtpassword;
    @FXML
      private Label lblwelcome;
    @FXML
    private Button btnsave;
    @FXML
    private Button btncancel;
       private Member member;
       private Account account;
       DBConnection dbhandler;
    public void initialize() {
        dbhandler = new DBConnection();
        account = SceneData.getInstance().getLoggedInMember();

        assert (account != null) : "Fatal error, customer shall never be null here!";


        txtssn.setDisable(true);   // cant change this fields
        txtemail.setText(account.getEmail());
        txtpassword.setText(account.getPassWord());

        lblwelcome.setText("Account:" + account.getEmail());


        ArrayList<Account> accounts = DBConnection.getInstance().getAccounts(account.getEmail(),account.getPassWord());


        }




    public void btnupdateInformation() {


        DBConnection.getInstance().updateAccount(account.getEmail(),account.getPassWord(),account.getMember_SSN());


    }
    public void cancel() {

    }

}
