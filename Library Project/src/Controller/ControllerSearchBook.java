package Controller;


import Model.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.DbUtil;
import sample.SceneData;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.sql.Date;
import java.util.ResourceBundle;

public class ControllerSearchBook {

    @FXML
    private AnchorPane anchorPane;

    public static String booktitle;
    @FXML
    private TableView<Book> tableview;
    @FXML
    private TableColumn<Book, String> titlecol;
    @FXML
    private TableColumn<Book, String> authorcol;
    @FXML
    private TableColumn<Book, String> isbncol;
    @FXML
    private TableColumn<Book, Integer> booktypecol;
    @FXML
    private TableColumn<Book, Boolean> availablecol;

    @FXML
    private TextField BookTitle;
    @FXML
    private ListView<String> listviewsearch;


    public void initialize() throws IOException {
        //anchorPane.setMaxSize();

        DbUtil.getInstance();


    }


    boolean condi;
    boolean brrcon = false;
    int count = 0;

    DbUtil dbhandler = new DbUtil();


    public void SearchBook() throws IOException {
        // clearBookSearch();
        ObservableList<String> List = FXCollections.observableArrayList();
        String Title = BookTitle.getText();
        String qu = "SELECT * FROM book WHERE title = '" + Title + "'";
        ResultSet result = dbhandler.ExecuteQuery(qu);
        System.out.println(Title);
        List.add("Book information");
        boolean value = false;
        try {
            while (result.next()) {
                //Adding the columns i am going to use
                String bookTitle = Title;


                int ISBN = result.getInt("isbn");
//                    String Author = result.getString("author");
                //Boolean Available = result.getBoolean("available");
                List.add("Book Title " + bookTitle);
                List.add("Book ISBN:" + ISBN);
                List.add("Book Author : " + result.getString("author"));
                //String Status = (Available) ? "Available" : "Not Available";
                //List.add("Available :" + Status);

                // adding the columns to my javafx
//                    isbncol.setText(String.valueOf(ISBN));
//                    authorcol.setText(Author);
//                    // this one changes depenting on if the book was issued or not
//                    String status = (Available) ? "Available" :  "Not Available";
//                    availablecol.setText(status);


                value = true;
            }

            if (value == false) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Error, Couldn't find any book with that name!");
                alert.showAndWait();
                return;
            }


        } catch (Exception ex) {
            ex.printStackTrace();


        }


        listviewsearch.getItems().setAll(List);

    }

    public void homePage(ActionEvent event) throws IOException {

        Parent list = FXMLLoader.load(getClass().getResource("/Home/home.fxml"));
        Scene scene = new Scene(list);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Home page");
        window.setScene(scene);
        window.show();
    }


    public void borrowInfo(String title) {
        //Calendar cl = Calendar.getInstance();
        Calendar currenttime = Calendar.getInstance();
        currenttime.add(Calendar.DAY_OF_MONTH, 10);
        Date sqldate = new Date((currenttime.getTime()).getTime());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("*********** Borrowed books ********\n" + "Book Tiltle: " + title +
                "\nBorrowed date:  " + sqldate +
                "\nReturn Date:    " + sqldate);
        alert.setTitle("Borrow Info");

        //alert.setHeaderText("Woopppps");
        //alert.showAndWait();
        alert.show();

    }

    public static void borrAlert(String infoMessage, String headerText, String title) {


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText("Woopppps");
        //alert.showAndWait();
        alert.show();
    }

    public void reserve() throws SQLException {

        String qu = "SELECT * FROM book WHERE title = '" + BookTitle.getText() + "'";
        ResultSet result = dbhandler.ExecuteQuery(qu);
        Calendar currenttime = Calendar.getInstance();
        Date sqldatenow = new Date((currenttime.getTime()).getTime());
        currenttime.add(Calendar.DAY_OF_MONTH, 3);
        Date sqldate = new Date((currenttime.getTime()).getTime());


        while (result.next()) {

            if (SceneData.getInstance().getLoggedInMember() == null){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please log in", ButtonType.OK);
                alert.showAndWait();
                return;
            }

            if (result.getDate("reserved_until") == null && SceneData.getInstance().getLoggedInMember() != null) {
                String qu2 = "UPDATE book " +
                        "SET reserved_by = " +
                        "(SELECT member_id FROM member WHERE member_id = '" + SceneData.getInstance().getLoggedInMember().getMember_SSN() + "')" +
                        ",reserved_until = '" + sqldate + "';";
                dbhandler.executeAction(qu2);
            } else if (result.getDate("reserved_until") != null);{
                if( result.getDate("reserved_until").after(sqldatenow)){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Book is already reserved? pleased try again");
                    alert.showAndWait();
                }
            }  if (SceneData.getInstance().getLoggedInMember() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Please log in before reserving!");
                alert.showAndWait();
            }
        }
    }
//        public void clearBookSearch() {
//
//           listviewsearch.setEditable(false);
//
//            isbncol.setText("");
//            authorcol.setText("");
//            availablecol.setText("");
//
//        }
    }

// this method is used to set my javafx columns
    /*    private void colum() {
            titlecol.setCellValueFactory(new PropertyValueFactory<>("Title"));
            isbncol.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
            authorcol.setCellValueFactory(new PropertyValueFactory<>("Author"));
            booktypecol.setCellValueFactory(new PropertyValueFactory<>("BookType"));
            availablecol.setCellValueFactory(new PropertyValueFactory<>("availiability"));
        }


    }*/
