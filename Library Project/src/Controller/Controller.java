package Controller;

import Model.Account;
import Model.Member;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.DbUtil;
import sample.SceneData;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Controller {
    private Account member;
    DbUtil dbhandler;
    public void initialize() {

        member = SceneData.getInstance().getLoggedInMember();
//
//
        assert (member != null) : "Fatal error, member should  never be null here!";

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
        ArrayList<Account> accounts = DbUtil.getInstance().getAccounts(member.getEmail(), member.getPassWord());
//
        if (accounts.size() > 0) {
            // you should be able to change information in your settings such as email and password

        }
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

    public void loadAddBook() {
        loadMenu("../View/Addbook.fxml", "Add Book");

    }

    public void loadBookView() {
        loadMenu("../View/Book_List.fxml", "Book List");


    }

    public void loadAddMember() {
        loadMenu("../View/AddMember.fxml", "Add Member");

    }

    public void loadMemberView() {
        loadMenu("../View/Member_List.fxml", "Member List");

    }

    public void loadSettings() {
        loadMenu("../View/Settings.fxml", "Settings");

    }

    public void loadSearchBook() {
        loadMenu("../View/SearchBook.fxml", "Search Book");


    }

    public void  loadIssueBook() {
        loadMenu("../View/IssueBook.fxml", "Issue Book");
    }

    public void loadBookCatalog() {
        loadMenu("../View/BookCategory.fxml", "Book Catalog");

    }
    public void loadReturnBook() {
        loadMenu("../View/ListOfIssues.fxml","Return Book");
    }
}
