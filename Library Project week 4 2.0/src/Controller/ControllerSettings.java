package Controller;

import Model.Account;
import Model.Member;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private TextField txtname;
    @FXML
    private TextField txtaddress;
    @FXML
    private TextField txtphonenumber;
    @FXML
    private TextField txtemail;

    @FXML
    private Button btnsave;
    @FXML
    private Button btncancel;
       private Member member;
       DbUtil dbhandler;
    public void initialize() {
        dbhandler = new DbUtil();
        member = SceneData.getInstance().getLoggedInMember();

        //txtssn.setDisable(true);
       // txtname.setDisable(true);   // cant change this fields

          txtssn.setText(member.getSSN());
        txtname.setText(member.getName());
        txtaddress.setText(member.getAddress());
        txtemail.setText(member.getEmail());
        txtphonenumber.setText(member.getPhoneNumber());
        ArrayList<Account> accounts = DbUtil.getInstance().getAccounts(member.getSSN());

        if (accounts.size() > 0 ) {
            btnupdateInformation();

        }



    }
    public void btnupdateInformation() {

        member.setEmail(txtaddress.getText());
        member.setAddress(txtaddress.getText());
        member.setPhoneNumber(txtphonenumber.getText());
        DbUtil.getInstance().updateMember(member.getSSN(),member.getAddress(),member.getPhoneNumber(),member.getEmail());


    }
    public void cancel() {

    }

}
