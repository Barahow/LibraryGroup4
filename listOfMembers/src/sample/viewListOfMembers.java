package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class viewListOfMembers implements Initializable{
    @FXML
    private TableView <Member> myTable;
    @FXML private TableColumn<Member, String> memberidColumn;
    @FXML private TableColumn<Member, String> memberssnColumn;
    @FXML private TableColumn<Member, String> firstNameColumn;
    @FXML private TableColumn<Member, String> lastNameColumn;

    @FXML private TextField firstNameTextField;
    @FXML private TextField lastNameTextField;
    @FXML private TextField memberssnTextField;
    @FXML private TextField memberidTextField;




    @Override
    public void initialize(URL location, ResourceBundle resources) {
/*
        TableColumn memberlibraryId = new TableColumn( "Member Library ID" );
        TableColumn memberssn = new TableColumn( "Member SSN" );
        TableColumn firstName = new TableColumn( "first name" );
        TableColumn lastName = new TableColumn( "last name" );
        myTable.getColumns().addAll( memberlibraryId, memberssn, firstName, lastName  );
*/
        myTable.setEditable( true );
        firstNameColumn.setCellValueFactory( new PropertyValueFactory<Member, String>("firstName"  ) );
        lastNameColumn.setCellValueFactory( new PropertyValueFactory<Member, String>("lastName"  ) );
        memberidColumn.setCellValueFactory( new PropertyValueFactory<Member, String>("memberid"  ) );
        memberssnColumn.setCellValueFactory( new PropertyValueFactory<Member, String>("memberssn"  ) );

        myTable.getSelectionModel().setSelectionMode( SelectionMode.MULTIPLE );

        myTable.setItems( getPeople() );

    } public void newMemberButtonPushed(){
        Member newMember = new Member( firstNameTextField.getText(), lastNameTextField.getText(), memberidTextField.getText(), memberssnTextField.getText() );
        myTable.getItems().add(newMember);
    }
    public void deletedButtonPushed(){
        ObservableList<Member> selectedRows, allPeople;
        allPeople = myTable.getItems();
        selectedRows = myTable.getSelectionModel().getSelectedItems();

        for (Member member: selectedRows) {
            allPeople.remove( member );
        }
    }

    public ObservableList<Member> getPeople(){
        ObservableList<Member> people = FXCollections.observableArrayList();
        people.add(new Member( "45", "husse", "978054-4810", "Kalil" ));


        return people;
    }


}


