package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.DBConnection;

public class ControllerAddBook {
    @FXML
    private TextField title;
    @FXML
    private TextField booktype;
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

    DBConnection databasehandler;

    public void initialize() {
//
        databasehandler = new DBConnection();

        ;

    }

    public  void addbook(ActionEvent event) {
        String BookISBN = isbn.getText();
        String BookTitle = title.getText();
        String BookAuthor = author.getText();
        String BookType= booktype.getText();

             // if you don't write on all colums you get an error message
        if (BookISBN.isEmpty() || BookTitle.isEmpty() || BookAuthor.isEmpty() || BookType.isEmpty()) {
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
        String qu = "INSERT INTO book VALUES ("+
                "" + BookISBN + ","+
                "'" + BookTitle + "'," +
                "'" + BookAuthor + "'," +
                "'" + BookType + "'," +
                "" +  true + "," +
                "" +  false + "" +
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


