package Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import model.Account;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.DBConnection;
import sample.SceneData;
import java.io.IOException;
import java.util.ArrayList;

public class Controller {
    private Account member;
    DBConnection dbhandler;

    public Controller(){

    }

    @FXML
    private void initialize() {

        member = SceneData.getInstance().getLoggedInMember();
//
//
        assert (member != null) : "Fatal error, member shall never be null here!";

/*
       Boolean Membertype = true;
        String qu = "SELECT * FROM member WHERE membertype = '" + Membertype+ "'";
        ResultSet result = dbhandler.ExecuteQuery(qu);

        //boolean value = false;
        try {
            while (result.next()) {
               loadBookCatalog();
               loadSearchBook();
               loadSettings();




            }

            if (Membertype == false) {
               loadAddBook();
               loadBookCatalog();
               loadSearchBook();
               loadMemberView();
               loadSettings();
               loadIssueBook();
               loadBookView();


            }


        } catch (Exception ex) {
            ex.printStackTrace();


        }*/


        //Find the account object for this customer.
        //ArrayList<Account> accounts = DBConnection.getInstance().getAccounts(member.getEmail(), member.getPassWord());
//
        //if (accounts.size() > 0) {
            // you should be able to change information in your settings such as email and password

        //}
    }


    public void loadMenu(String location, String title) {

        try {
            Parent parent = FXMLLoader.load(getClass().getResource(location));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    // these methods loads the Main Menu

    @FXML
    private void loadAddBook() {
        loadMenu("../view/Addbook.fxml", "Add Book");

    }

    @FXML
    public void loadBookView() {
        loadMenu("../view/Book_List.fxml", "Book List");


    }

    public void loadAddMember() {
        loadMenu("../view/AddMember.fxml", "Add Member");

    }

    public void loadMemberView() {
        loadMenu("../view/Member_List.fxml", "Member List");

    }

    public void loadSettings() {
        loadMenu("../view/Settings.fxml", "Settings");

    }

    public void loadSearchBook() {
        loadMenu("../view/SearchBook.fxml", "Search Book");
    }

    public void loadSearchBookCatalog() {
        loadMenu("../view/search.fxml", "Search Catalog");
    }

    public void logIn() {
        loadMenu("../view/signIn.fxml", "Log in");
    }

    public void  loadIssueBook() {
        loadMenu("../view/IssueBook.fxml", "Issue Book");
    }

    public void loadBookCatalog() {
        loadMenu("../view/BookCategory.fxml", "Book Catalog");

    }
    public void loadListOfBooking() {

    }
}
