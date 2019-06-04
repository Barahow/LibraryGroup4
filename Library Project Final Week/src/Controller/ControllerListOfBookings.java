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

public class ControllerListOfBookings {

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


        String qu = "SELECT * FROM borrowedby";
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




}