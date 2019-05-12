package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.DbUtil;

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
    private TextField txtmembertype;

    @FXML
    private AnchorPane rootpane;

    @FXML
    private Button btnsave;
    @FXML
    private Button btncancel;

    DbUtil databasehandler;

    public void initialize() {
//
        databasehandler = new DbUtil();

    }

    public  void addMember(ActionEvent event) {
        String SSN = txtssn.getText();
        String Name = txtname.getText();
        String Address = txtaddress.getText();
        String PhoneNumber= txtphonenumber.getText();
        String Email= txtemail.getText();

        //String  MemberType=  txtmembertype.getText();

             // if you don't write on all colums you get an error message
        if (SSN.isEmpty() || Name.isEmpty() || Address.isEmpty() || PhoneNumber.isEmpty() || Email.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please enter in all fields");
            alert.showAndWait();
            return;

        }



//        + "  isbn int(11) primary key, \n"
//                + "   title varchar(45), \n"
//                + "  author varchar(45), \n"
//                + " bookcategory_typeid int(45), \n"
//                + " available boolean default true "
//                + ")");

        //this are the database queries.
        String qu = "INSERT INTO member VALUES ("+
                "'" + SSN + "',"+
                "'" + Name + "'," +
                "'" + Address+ "'," +
                "'" + PhoneNumber + "'," +
                "'" + Email + "'," +
                "" +  true + "" +
                ")";
        System.out.println(qu);

        if (databasehandler.executeAction(qu)){

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Succes");
            alert.showAndWait();


        } else //error

        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Failed");
            alert.showAndWait();

        }







    }

    public void cancel(ActionEvent event ) {
        // this will close the window
        Stage stage = (Stage)rootpane.getScene().getWindow();
        stage.close();


    }


}


