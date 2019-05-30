package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.DBConnection;

public class ControllerAddMember {
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
    private PasswordField txtpassword;

    @FXML
    private TextField txtmembertype;

    @FXML
    private AnchorPane rootpane;

    @FXML
    private Button btnsave;
    @FXML
    private Button btncancel;

    DBConnection databasehandler;

    public void initialize() {
//
        databasehandler = new DBConnection();

    }

    public void addMember(ActionEvent event) {
        String SSN = txtssn.getText();
        String Name = txtname.getText();
        String Address = txtaddress.getText();
        String PhoneNumber = txtphonenumber.getText();
        String Email = txtemail.getText();
        String Password = txtpassword.getText();

        //String  MemberType=  txtmembertype.getText();

        // if you don't write on all colums you get an error message

        if (SSN.matches("^\\d{6}(\\d{2})?[-+]\\d{4}$")) {

            if (PhoneNumber.matches("\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}")) {
                String qu = "INSERT INTO member VALUES (" +
                        "'" + SSN + "'," +
                        "'" + Name + "'," +
                        "'" + Address + "'," +
                        "'" + PhoneNumber + "'," +
                        "'" + Email + "'," +
                        "" + true + "" +
                        ")";
                String qu2 = "INSERT INTO account VALUES (" +
                        "'" + Email + "'," +
                        "'" + Password + "'," +
                        "'" + SSN + "'" +
                        ")";
                System.out.println(qu);

                if (databasehandler.executeAction(qu) && databasehandler.executeAction(qu2)) {

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Succes");
                    alert.showAndWait();
                }
                // you need to enter in all fields
                if (SSN.isEmpty() || Name.isEmpty() || Address.isEmpty() || PhoneNumber.isEmpty() || Email.isEmpty()) {
                    errorEnterFields();
                    return;
                }else {
                    // if it the phone number isn't 10 digits you get an error
                    validatePhonenumberError();
                }

            } else {
                // if the SSN doesn't follow this format yymmdd-xxxx you get an error
                validateSSNError();
                return;
            }

        } else {
            // Database error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Failed");
            alert.showAndWait();
        }
    }


    private void errorEnterFields() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText("Please enter in all fields");
        alert.showAndWait();
        return;
    }

    public void cancel(ActionEvent event) {
        // this will close the window
        Stage stage = (Stage) rootpane.getScene().getWindow();
        stage.close();


    }

    private void validateSSNError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText("Error, the SSN security number needs to follow this format yymmdd-nnnn and Phonenumber needs to be 10 digits");
        alert.showAndWait();

    }

    private void validatePhonenumberError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText("Error Phonenumber needs to be 10 digits");
        alert.showAndWait();
    }
}