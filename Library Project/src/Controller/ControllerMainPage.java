package Controller;

import Model.Account;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.DbUtil;
import sample.Main;
import sample.SceneData;

import java.io.IOException;
import java.util.ArrayList;

public class ControllerMainPage {


    DbUtil dbhanlder;


    public void initialize() {


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

    public void loadSearchBook() {
        loadMenu("../View/SearchBook.fxml", "Search Book");


    }


    public void loadBookView() {
        loadMenu("../View/Book_List.fxml", "Book List");


    }

    public void loadBookCatalog() {
        loadMenu("../View/BookCategory.fxml", "Book Catalog");

    }

    public void loadLoginView() {
        Main.loadLoginView();
    }
}
