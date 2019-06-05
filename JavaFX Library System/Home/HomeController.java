package Home;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {


    public void searchPage(ActionEvent event) throws Exception {
        Parent list = FXMLLoader.load(getClass().getResource("/Search/Search.fxml"));
        Scene scene = new Scene(list);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("SearchPage");
        window.setScene(scene);
        window.show();
    }

    @FXML
    public void signInPage(ActionEvent event) throws Exception {
        Parent list = FXMLLoader.load(getClass().getResource("/Login/signIn.fxml"));
        Scene scene = new Scene(list);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("SignInPage");
        window.setScene(scene);
        window.show();

    }

   /*@FXML
    public void signUpPage(ActionEvent event) throws Exception {
        Parent list = FXMLLoader.load(getClass().getResource("/Login/SignUp.fxml"));
        Scene scene = new Scene(list);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("SignUpPage");
        window.setScene(scene);
        window.show();
    }*/


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}





