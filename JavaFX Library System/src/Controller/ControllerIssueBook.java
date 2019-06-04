package Controller;


import model.Book;
import model.Member;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import sample.DBConnection;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.Optional;

public class ControllerIssueBook {

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
    private TableColumn<Member, String> namecol;
    @FXML
    private TableColumn<Member, String> emailcol;
    @FXML
    private TableColumn<Member, String> addresscol;


    @FXML
    private TextField txtsearch;
    @FXML
    private TextField txtssn;
    @FXML
    private TextField txtisbn;
    @FXML
    private TextField txtduedate;
    @FXML
    private TextField txtreturndate;

    DBConnection dbhandler;

    public void initialize() throws IOException {
        //anchorPane.setMaxSize();


        dbhandler = new DBConnection();


    }


    boolean condi;
    boolean brrcon = false;
    int count = 0;

//    DBConnection dbhandler = new DBConnection();

    ObservableList<Book> List = FXCollections.observableArrayList();

    public void IssueBook() throws IOException {
        //  clearBookSearch();

        String BookISBN = txtisbn.getText();
        String MemberSSN = txtssn.getText();
        String DueDate = txtduedate.getText();
        String ReturnDate = txtreturndate.getText();
        String IssueTime = null;

        // if you don't write on all colums you get an error message
        if (BookISBN.isEmpty() || MemberSSN.isEmpty() || DueDate.isEmpty() || ReturnDate.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please enter in all fields");
            alert.showAndWait();
            return;

        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText("Do you want to Issue this book," + txtisbn.getText() + " To this Member" + txtssn.getText() + "?");
        alert.showAndWait();
        Optional<ButtonType> answer = alert.showAndWait();
        if (answer.get() == ButtonType.OK) {
            //this are the database queries.
            String qu = "INSERT INTO borrowedby VALUES (" +
                    "'" + MemberSSN + "'," +
                    "'" + BookISBN + "'," +
                    "'" + DueDate + "'," +
                    "'" + ReturnDate + "'," +
                    "" + IssueTime + "" +
                    ")";
            String que2 = "UPDATE book SET available = false WHERE isbn = '" + BookISBN + "'";
            System.out.println(qu + " and " +  que2);

            if (dbhandler.executeAction(qu)|| dbhandler.executeAction(que2)) {

                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setHeaderText(null);
                alert1.setContentText("Succes");
                alert1.showAndWait();


            } else //error

            {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setHeaderText(null);
                alert1.setContentText("Failed");
                alert1.showAndWait();

            }


        }
    }


     public void searcBook() {

    clearBookSearch();

    String Title = txtsearch.getText();
        String qu = "SELECT * FROM book WHERE title = '" + Title + "'";
        ResultSet result = dbhandler.ExecuteQuery(qu);
        boolean value = false;
        try {
            while (result.next()) {
                //Adding the columns i am going to use

                int ISBN = result.getInt("isbn");
                String Author = result.getString("author");
                Boolean Available = result.getBoolean("available");

                // adding the columns to my javafx
                isbncol.setText(String.valueOf(ISBN));
                authorcol.setText(Author);
                // this one changes depenting on if the book was issued or not
                String status = (Available) ? "Available" : "Not Available";
                availablecol.setText(status);


                value = true;
            }

            if (ControllerSearchBook.errorHandling(value)) return;


        } catch (Exception ex) {
            ex.printStackTrace();


        }
    }

        public void clearBookSearch() {
            isbncol.setText("");
            authorcol.setText("");
            availablecol.setText("");

        }
    }
