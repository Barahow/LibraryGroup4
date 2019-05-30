package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.DBConnection;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerEditBook implements Initializable {

    @FXML private TextField titletxt;
    @FXML private TextField catagorytxt;
    @FXML private Label titleofbook;
    @FXML private Label catagoryofbook;
    @FXML private Button editbutton;
    @FXML private Button closePage;

    DBConnection dbhandler;   // Database connection instance.

    /**
     * This method updates the book type.
     * @param event
     */
    public void updateBooks(ActionEvent event){
        String editTitle = titletxt.getText();
        int editCatagory = Integer.parseInt(catagorytxt.getText());
        dbhandler = new DBConnection();
        dbhandler.updateBookCatagory(editTitle,editCatagory);
    }

    /**
     * THis method close the current window.
     * @param event
     */

    public  void close(ActionEvent event){
        Stage window = (Stage) closePage.getScene().getWindow();
        window.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}