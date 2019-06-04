package Controller;


import javafx.event.ActionEvent;
import model.Book;
import model.BookCategory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import sample.DBConnection;

import java.io.IOException;
import java.sql.ResultSet;

public class ControllerBookCategory {

    @FXML
    private AnchorPane anchorPane;

    public static String booktitle;

    @FXML
    private TableColumn<BookCategory, String> titlecol;
    @FXML
    private TableColumn<BookCategory, String> authorcol;
    @FXML
    private TableColumn<BookCategory, Integer> typeidcol;
    @FXML
    private TableColumn<BookCategory, String> categorycol;
    @FXML
    private TableColumn<Book, Boolean> availablecol;
    @FXML
    private TextField booktype;
    @FXML
    private TableView<BookCategory> tableview;


    public void initialize() throws IOException {
        //anchorPane.setMaxSize();

        DBConnection.getInstance();
        initcolum();


    }




    DBConnection dbhandler = new DBConnection();


    public void bookTypePressed(ActionEvent event) throws IOException {
        Button button = (Button)event.getSource();
        String type = button.getText();
        // clearBookSearch();
        ObservableList<BookCategory> List = FXCollections.observableArrayList();
        int typeid= Integer.parseInt(type);
        String qu =  "SELECT typeid, title, author, booktype FROM bookcategory join book where bookcategory_typeid = typeid and typeid = '" + typeid + "'";

//                "SELECT * FROM bookcategory WHERE booketype = '" + BookType + "'";
        ResultSet result = dbhandler.ExecuteQuery(qu);


       // List.add("Book Category");
        boolean value = false;
        try {
            while (result.next()) {
                //Adding the columns i am going to use
                int BookTypeid;
                BookTypeid = result.getInt("typeid");
                String Author = result.getString("author");
                String Title = result.getString("title");
                String bookType = result.getString("booktype");

                List.add(new BookCategory(BookTypeid,Title,Author,bookType));

             /*   List.add("Book TypeID\n" + booktypeid);
                List.add(" title\n " + result.getString("title"));
                List.add("Book Author\n : " + result.getString("author"));
                List.add("Book Category\n" + result.getString("booktype"));*/
               /* String Author = resultSet.getString("author");
                String Title = resultSet.getString("title");
                int bookTypeId = resultSet.getInt("bookcategory_typeid");
*/
                // adding the columns to my javafx
               // List.add(new BookCategory(Title, Author, bookType));

                value = true;
            }

            if (value == false) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Error, Couldn't find any category with that ID!");
                alert.showAndWait();
                return;
            }


        } catch (Exception ex) {
            ex.printStackTrace();


        }


        tableview.setItems(List);

    }
    private void initcolum() {

        typeidcol.setCellValueFactory(new PropertyValueFactory<>("TypeId"));
        titlecol.setCellValueFactory(new PropertyValueFactory<>("Title"));
      authorcol.setCellValueFactory(new PropertyValueFactory<>("Author"));
        categorycol.setCellValueFactory(new PropertyValueFactory<>("BookType"));

    }


}