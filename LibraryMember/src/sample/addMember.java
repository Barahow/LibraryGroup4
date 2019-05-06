package sample;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class addMember implements Initializable {


    @FXML
    private TextField memberid ;
    @FXML
    private   TextField memberssn;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private Button addmemberbtn;
    @FXML
    private Button canelBTN;



    @FXML
    private void addMemberBTN(ActionEvent event){

    }
    @FXML
    private void canelBTN(ActionEvent event){

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
