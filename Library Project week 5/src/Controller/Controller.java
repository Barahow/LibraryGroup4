package Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Controller {

    public void initialize() {
//


    }

    public void loadMenu(String location, String title) {

        try {
           Parent parent  = FXMLLoader.load(getClass().getResource(location));
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
        loadMenu("../View/Addbook.fxml","Add Book");

    }

    public void loadBookView() {
        loadMenu("../View/Book_List.fxml","Book List");


    }
    public void loadAddMember() {
        loadMenu("../View/AddMember.fxml","Add Member");

    }
    public void loadMemberView() {
        loadMenu("../View/Member_List.fxml","Member List");

    }

    public void loadSettings() {

    }
    public void loadSearchBook() {
        loadMenu("../View/SearchBook.fxml","Search Book");


    }
}
