package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Book;
import sample.DBConnection;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;


public class ControllerBookList2 {
    // Creat an obserable list of books collection
    ObservableList<Book> List = FXCollections.observableArrayList();

    @FXML
    private AnchorPane rootpane;
    @FXML
    private TableView<Book> tableview;
    @FXML
    private TableColumn<Book, String> titlecol;
    @FXML
    private TableColumn<Book, Integer> booktypecol;
    @FXML
    private TableColumn<Book, String> authorcol;
    @FXML
    private TableColumn<Book, String> isbncol;
    @FXML
    private TableColumn<Book, Boolean> availabilitycol;



    public void initialize() {


        initcolum();

        initData();

    }

    private void initData() {

        DBConnection handler = new DBConnection();
        String qu = "SELECT * FROM book";
        ResultSet resultSet = handler.ExecuteQuery(qu);
        try {
            while (resultSet.next()) {
                String Author = resultSet.getString("author");
                String Title = resultSet.getString("title");
                int bookTypeId = resultSet.getInt("bookcategory_typeid");
                int ISBN = resultSet.getInt("isbn");
                Boolean Availability = resultSet.getBoolean("available");

                // adding them together
                List.add(new Book(ISBN,Title,Author, bookTypeId, Availability));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Adding my list to the table view;
        // update the list everytime a book is deleted
        tableview.setItems(List);

    }

    // this method is used to set my javafx columns
    private void initcolum() {
        titlecol.setCellValueFactory(new PropertyValueFactory<>("Title"));
        isbncol.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        authorcol.setCellValueFactory(new PropertyValueFactory<>("Author"));
        booktypecol.setCellValueFactory(new PropertyValueFactory<>("BookType"));
        availabilitycol.setCellValueFactory(new PropertyValueFactory<>("availability"));
    }

    @FXML
    private void handleDeleteBook(ActionEvent event) {
        Book selectBook = tableview.getSelectionModel().getSelectedItem();
        // If you havent selected a book to dleete you get an error
        if (selectBook == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Failed, Select a book to delete!");
            alert.showAndWait();
            return;
        }
        // now to confirm if you want to delete
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText("Do you want to delete this Book," + selectBook.getTitle() + " ?");
        alert.showAndWait();
        Optional<ButtonType> answer = alert.showAndWait();
        if (answer.get() == ButtonType.OK) {
            // if the anser is yes then the book will be deleted
            try {
                Boolean result = DBConnection.getInstance().deleteBook(selectBook);
                if (result == true) {
                    alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("The book " + selectBook.getTitle() + " was succesfully deleted");
                    alert.showAndWait();
                    List.remove(selectBook);

                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("The book " + selectBook.getTitle() + " Couldn't be deleted");
                    alert.showAndWait();

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            // if  the answer is no then it will be cancele
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Deletion Canceled");
            alert.showAndWait();


        }


    }
    public void handleEditBook() {

    }
}
///
 ////////////










