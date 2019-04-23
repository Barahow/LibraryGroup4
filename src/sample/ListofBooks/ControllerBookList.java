package sample.ListofBooks;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import sample.DbUtil;

import java.sql.ResultSet;
import java.sql.SQLException;


public class ControllerBookList {
    // Creat an obserable list of books collection
    ObservableList<Book> List = FXCollections.observableArrayList();

    @FXML
    private AnchorPane rootpane;
    @FXML
    private TableView<Book> tableview;
    @FXML
    private TableColumn<Book,String> titlecol;
    @FXML
    private TableColumn<Book,Integer> idcol;
    @FXML
    private TableColumn<Book,String> authorcol;
    @FXML
    private TableColumn<Book,String> isbncol;
    @FXML
    private TableColumn<Book,Boolean> availabilitycol;

    Book listofbooks;

    public void initialize() {

        initcolum();

        LoadData();

    }

    private void LoadData() {
        DbUtil handler = new DbUtil();
        String qu = "SELECT * FROM book";
        ResultSet resultSet = handler.ExecuteQuery(qu);
        try {
            while (resultSet.next()) {
                String Author= resultSet.getString("author");
                String Title = resultSet.getString("title");
                int ID = resultSet.getInt("book_id");
                String ISBN = resultSet.getString("ISBN");
                Boolean Availability = resultSet.getBoolean("availablilty");

                // adding them together
                List.add(new Book(Title,ID,Author,ISBN,Availability));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Adding my list to the table view;
        tableview.getItems().setAll(List);

    }


    private void initcolum() {
        titlecol.setCellValueFactory(new PropertyValueFactory<>("Title"));
      idcol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        authorcol.setCellValueFactory(new PropertyValueFactory<>("Author"));
        isbncol.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
      availabilitycol.setCellValueFactory(new PropertyValueFactory<>("availiability"));
    }


}







