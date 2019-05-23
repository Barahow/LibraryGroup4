package Controller;

import Model.Account;
import Model.Member;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import sample.DbUtil;
import sample.Main;
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
       DbUtil dbhandler;
    public void initialize() {
        dbhandler = new DbUtil();
        account = SceneData.getInstance().getLoggedInMember();



        assert (account != null) : "Fatal error, customer shall never be null here!";

        initvalues();
        btnupdateInformation();


        ArrayList<Account> accounts = DbUtil.getInstance().getAccounts(account.getEmail(),account.getPassWord());


        }




@FXML
    private void btnupdateInformation() {


        DbUtil.getInstance().updateAccount(account.getEmail(),account.getPassWord(),account.getMember_SSN());


    }
    public void cancel() {

    }

    @FXML
    private void  initvalues() {
        txtssn.setDisable(true);
        lblwelcome.setText("Account:" + account.getEmail());
        txtemail.setText(account.getEmail());
        txtpassword.setText(account.getPassWord());
    }

}
