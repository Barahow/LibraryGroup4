package Controller;

import Model.Member;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.DbUtil;

public class ControllerRegisterAccount {
    @FXML
    private TextField txtssn;
    @FXML
    private TextField txtemail;
    @FXML
    private PasswordField txtpassword;
    @FXML
    private TextField isbn;

    @FXML
    private AnchorPane rootpane;

    @FXML
    private Button btnsave;
    @FXML
    private Button btncancel;
    Member member;
    DbUtil databasehandler;

    public void initialize() {
//
        databasehandler = new DbUtil();

    }

    public void addAccount(ActionEvent event) {
        String SSN = txtssn.getText();
        String Email = txtemail.getText();
        String Password = txtpassword.getText();

        if (SSN.isEmpty() || Email.isEmpty() || Password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please enter in all fields");
            alert.showAndWait();
            return;
        }

        //this are the database queries.
        String qu = "INSERT INTO account VALUES (" +
                "'" + Email + "'," +
                "'" + Password + "'," +
                "'" + SSN + "'" +
                ")";
        System.out.println(qu);


        if (databasehandler.executeAction(qu)) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Succes");
            alert.showAndWait();


        } else //error

        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("No such SSn in the System, try again");
            alert.showAndWait();
            return;

        }
    }


        public void cancel (ActionEvent event ){
            // this will close the window
            Stage stage = (Stage) rootpane.getScene().getWindow();
            stage.close();


    }


    }



