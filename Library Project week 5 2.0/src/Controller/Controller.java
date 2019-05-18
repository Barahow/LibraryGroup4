package Controller;

import Model.Account;
import Model.Member;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.DbUtil;
import sample.SceneData;

import java.io.IOException;
import java.util.ArrayList;

public class Controller {
    private Account member;

    public void initialize() {
        member = SceneData.getInstance().getLoggedInMember();
//
//
        assert (member != null) : "Fatal error, customer shall never be null here!";
//
//        txtname.setDisable(true);   // cant change this fields
//        txtamount.setDisable(true);
//
//        txtname.setText(customer.getName());
//        txtaddress.setText(customer.getAddress());
//
//        lblwelcome.setText("Hello " + customer.getName() + ", welcome to your Bank Account.");
//
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

    public void loadIssueBook() {
        loadMenu("../View/IssueBook.fxml", "Issue Book");
    }

    public void loadBookCatalog() {
        loadMenu("../View/BookCategory.fxml", "Book Catalog");

    }
}
