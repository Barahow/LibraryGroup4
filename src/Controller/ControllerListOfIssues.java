package Controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Book;
import model.BookIssue;
import model.Member;
import sample.DBConnection;


import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ControllerListOfIssues {

    @FXML
    private AnchorPane anchorPane;

    public static String booktitle;

    @FXML
    private TableColumn<BookIssue, String> bookisbncol;
    @FXML
    private TableColumn<BookIssue, String> memberssncol;
    @FXML
    private TableColumn<BookIssue, String> duedatecol;
    @FXML
    private TableColumn<BookIssue, String> returndatecol;
    @FXML
    private TableColumn<BookIssue, String> issuedatecol;
    @FXML
    private TextField booktype;
    @FXML
    private TableView<BookIssue> tableview;
    ObservableList<Book> List = FXCollections.observableArrayList();
    private Member member;

   DBConnection dbhandler;
    public void initialize() throws IOException {
        //anchorPane.setMaxSize();
        // Creat an obserable list of issue collection


        DBConnection.getInstance();
        IssueBookList();
        initcolum();


    }


    public void IssueBookList() throws IOException {

        ObservableList<BookIssue> List = FXCollections.observableArrayList();
        DBConnection handler = new DBConnection();
        // String MemberSSN;


        String qu = "SELECT book_isbn , member_SSN, due_date, return_date, issue_date FROM borrowedby  join member where member_SSN = SSN";
        ResultSet resultSet = handler.ExecuteQuery(qu);
        try {
            while (resultSet.next()) {


                String Book_ISBN = resultSet.getString("book_isbn");
                String Member_SSN = resultSet.getString("member_SSN");
                String Due_Date = resultSet.getString("due_date");
                String Return_Date = resultSet.getString("return_date");
                String Issue_date = resultSet.getString("issue_date");

                // adding them together
                List.add(new BookIssue(Member_SSN, Book_ISBN, Due_Date, Return_Date, Issue_date));
            }

            tableview.setItems(List);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void initcolum() {

        bookisbncol.setCellValueFactory(new PropertyValueFactory<>("book_ISBN"));
        memberssncol.setCellValueFactory(new PropertyValueFactory<>("member_SSN"));
      duedatecol.setCellValueFactory(new PropertyValueFactory<>("due_Date"));
        returndatecol.setCellValueFactory(new PropertyValueFactory<>("return_Date"));
        issuedatecol.setCellValueFactory(new PropertyValueFactory<>("issue_Date"));
    }

    public void handleReturnBook() {
   dbhandler = new DBConnection();
        BookIssue selectBook = tableview.getSelectionModel().getSelectedItem();
        BookIssue SelectedBookID = tableview.getSelectionModel().getSelectedItem();
        // If you havent selected a book to dleete you get an error
        if (selectBook == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Failed, Select a book to Return!");
            alert.showAndWait();
            return;
        }
        // now to confirm if you want to delete
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText("Do you want to retunr this Book," + selectBook.getBook_ISBN() + " ?");
        alert.showAndWait();
        Optional<ButtonType> answer = alert.showAndWait();
        if (answer.get() == ButtonType.OK) {
            // if the anser is yes then the book will be deleted
            String q = "DELETE FROM borrowedby WHERE book_isbn = '" + selectBook.getBook_ISBN() + "'";
            String q2 = "UPDATE book SET available = true where isbn = '" + selectBook.getBook_ISBN() + "'";
            if (dbhandler.executeAction(q) && dbhandler.executeAction(q2)) {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText(null);
                alert.setContentText("The book " + selectBook.getBook_ISBN() + " was succesfully returned");
                alert.showAndWait();
                List.remove(selectBook);

            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("The book " + selectBook.getBook_ISBN() + " Couldn't be returned");
                alert.showAndWait();

            }

        } else {
            // if  the answer is no then it will be cancele
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Return Canceled");
            alert.showAndWait();


        }


    }


}