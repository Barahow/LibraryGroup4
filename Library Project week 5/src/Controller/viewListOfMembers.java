package Controller;

import Model.Book;
import Model.Member;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.DbUtil;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class viewListOfMembers {

    ObservableList<Member> List = FXCollections.observableArrayList();
    @FXML
    private TableView<Member> myTable;
    @FXML
    private TableColumn<Member, String> ssncol;
    @FXML
    private TableColumn<Member, String> namecol;
    @FXML
    private TableColumn<Member, String> addresscol;
    @FXML
    private TableColumn<Member, String> Phonecol;
    @FXML
    private TableColumn<Member, String> emailcol;
    @FXML
    private TableColumn<Member, Boolean> membertypecol;


    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField memberssnTextField;
    @FXML
    private TextField memberidTextField;


    public void initialize() {
        colum();
        initData();


//        TableColumn memberlibraryId = new TableColumn( "Member Library ID" );
//        TableColumn memberssn = new TableColumn( "Member SSN" );
//        TableColumn firstName = new TableColumn( "first name" );
//        TableColumn lastName = new TableColumn( "last name" );
//        myTable.getColumns().addAll( memberlibraryId, memberssn, firstName, lastName  );
//
//        */
//        initcolum();
//
//        LoadData();
//
//        myTable.setEditable( true );
//        firstNameColumn.setCellValueFactory( new PropertyValueFactory<Member, String>("firstName"  ) );
//        lastNameColumn.setCellValueFactory( new PropertyValueFactory<Member, String>("lastName"  ) );
//        memberidColumn.setCellValueFactory( new PropertyValueFactory<Member, String>("memberid"  ) );
//        memberssnColumn.setCellValueFactory( new PropertyValueFactory<Member, String>("memberssn"  ) );
//
//        myTable.getSelectionModel().setSelectionMode( SelectionMode.MULTIPLE );
//
//        myTable.setItems( getPeople() );
    }


    private void initData() {

        DbUtil handler = new DbUtil();
        String qu = "SELECT * FROM member";
        ResultSet resultSet = handler.ExecuteQuery(qu);
        try {
            while (resultSet.next()) {
                String SSN = resultSet.getString("SSN");
                String Name = resultSet.getString("name");
                String Address = resultSet.getString("address");
                String PhoneNumber = resultSet.getString("phone_number");
                String Email = resultSet.getString("email");
                Boolean MemberType = resultSet.getBoolean("membertype");

                // adding them together
                List.add(new Member(SSN, Name, Address, PhoneNumber, Email, MemberType));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        myTable.setItems(List);
    }

    public void deletedButtonPushed() {
        ObservableList<Member> selectedRows, allPeople;
        allPeople = myTable.getItems();
        selectedRows = myTable.getSelectionModel().getSelectedItems();

        for (Member member : selectedRows) {
            allPeople.remove(member);
        }
    }

    private void colum() {
        ssncol.setCellValueFactory(new PropertyValueFactory<>("SSN"));
        namecol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addresscol.setCellValueFactory(new PropertyValueFactory<>("address"));
        Phonecol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        emailcol.setCellValueFactory(new PropertyValueFactory<>("email"));
        membertypecol.setCellValueFactory(new PropertyValueFactory<>("membertype"));
    }

    @FXML
    private void handleDeleteMember(ActionEvent event) {
        Member selectmember = myTable.getSelectionModel().getSelectedItem();
        // If you havent selected a book to dleete you get an error
        if (selectmember == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Failed, Select a book to delete!");
            alert.showAndWait();
            return;
        }
        // now to confirm if you want to delete
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText("Do you want to delete this member," + selectmember.getName() + " ?");
        alert.showAndWait();
        Optional<ButtonType> answer = alert.showAndWait();
        if (answer.get() == ButtonType.OK) {
            // if the anser is yes then the book will be deleted
            try {
                Boolean result = DbUtil.getInstance().deletemember(selectmember);
                if (result == true) {
                    alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("The member " + selectmember.getName() + " was succesfully deleted");
                    alert.showAndWait();
                    List.remove(selectmember);

                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("The member " + selectmember.getName() + " Couldn't be deleted");
                    alert.showAndWait();

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            // if  the answer is no then it will be cancele
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Deletion Canceled");
            alert.showAndWait();


        }


    }
}










