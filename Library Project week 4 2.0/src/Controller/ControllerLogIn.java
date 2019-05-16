package Controller;

import Model.Member;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.DbUtil;
import sample.Main;
import sample.SceneData;

import java.io.IOException;
import java.util.ArrayList;

public class ControllerLogIn {

    @FXML
    private TextField txtfieldname;

    @FXML
    private Label lblstatus;
    @FXML
    private TextField Email;

    @FXML
    private TextField txtpassword;

    DbUtil dbhanlder;

    public void initialize() {

        lblstatus.setText("Status: Waiting for login");


    }

    public void Onkeypressed(KeyEvent keyevent) {
        if (keyevent.getCode().equals(KeyCode.ENTER)) {
            tryLogin(txtfieldname.getText());
        }
    }

    public void btnLoginPressed(ActionEvent event) {
        tryLogin(txtfieldname.getText());
    }

    private void tryLogin(String logInSSN) {

        ArrayList<Member> member = DbUtil.getInstance().getMemberSSN(logInSSN);

        if (member.size() == 0) {


            lblstatus.setText("Status: No member with SSN " + logInSSN + " Please try again");
            return;
        }

        Member loginMember = member.get(0);

        SceneData.getInstance().setLoggedInCustomer(loginMember);

        Main.loadLibraryView();

    }
}
