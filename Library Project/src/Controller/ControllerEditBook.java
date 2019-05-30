package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Book;
import sample.DBConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;


public class ControllerEditBook {
    // Creat an obserable list of books collection
    ObservableList<Book> List = FXCollections.observableArrayList();

    @FXML
    private TextField title;
    @FXML
    private TextField booktype;
    @FXML
    private TextField author;
    @FXML
    private TextField isbn;
    @FXML
    private TextField isbnsearch;


    @FXML
    private AnchorPane rootpane;
    DBConnection dbhandler;

    public void initialize() {



    }

    public void editBook() {

        String ISBN = isbnsearch.getText();
        String qu = "SELECT * FROM book WHERE title = '" + ISBN + "'";
        ResultSet result = dbhandler.ExecuteQuery(qu);
        try {
            while (result.next()){

            String ISBNSearch = ISBN;
                result.getString(ISBNSearch);
            }
            }catch (SQLException e) {
            e.printStackTrace();
        }

        String BookISBN = isbn.getText();
        String BookTitle = title.getText();
        String BookAuthor = author.getText();
        String BookType = booktype.getText();

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
        String qu2 = "UPDATE book SET (" +
                "'" + BookISBN + "'," +
                "'" + BookTitle + "'," +
                "'" + BookAuthor + "'," +
                "'" + BookType + "'," +
                "" + true + "" +
                ")";
        System.out.println(qu2);

        if (dbhandler.executeAction(qu2)) {

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


    public  void cancel() {

    }


}








