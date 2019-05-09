package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.DbUtil;

public class ControllerAddBook {
    @FXML
    private TextField title;
    @FXML
    private TextField id;
    @FXML
    private TextField author;
    @FXML
    private TextField isbn;

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

    public  void addbook(ActionEvent event) {
        String BookID = id.getText();
        String BookTitle = title.getText();
        String BookAuthor = author.getText();
        String BookISBN = isbn.getText();

             // if you don't write on all colums you get an error message
        if (BookID.isEmpty() || BookTitle.isEmpty() || BookAuthor.isEmpty() || BookISBN.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please enter in all fields");
            alert.showAndWait();
            return;

        }



        // if not empty
//      stmt.execute("CREATE TABLE " + Table_Name + '('
//              + "id int(200) primary key , \n"
//              + "  title varchar(200),\n"
//              + " author varchar(200), \n"
//              + " ISBN varchar(200), \n"
//              + " availablilty boolean default true ,"
//              + ')');

        //this are the database queries.
        String qu = "INSERT INTO book VALUES ("+
                "'" + BookID + "',"+
                "'" + BookTitle + "'," +
                "'" + BookAuthor + "'," +
                "'" + BookISBN + "'," +
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


