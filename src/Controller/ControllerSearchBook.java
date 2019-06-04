package Controller;


   import model.Book;
 //  import com.mysql.cj.protocol.Resultset;
   import javafx.collections.FXCollections;
   import javafx.collections.ObservableList;
   import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
   import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
   import javafx.scene.control.*;
   import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
   import sample.DBConnection;
   import sample.SceneData;

   import java.io.IOException;
   import java.sql.ResultSet;
   import java.sql.SQLException;
   import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
   import java.util.Calendar;
   import java.util.Date;

public class  ControllerSearchBook {

    @FXML
    private AnchorPane anchorPane;

    public static String booktitle;
    @FXML
    private TableView<Book> tableview;
    @FXML
    private TableColumn<Book, String> titlecol;
    @FXML
    private TableColumn<Book, String> authorcol;
    @FXML
    private TableColumn<Book, String> isbncol;
    @FXML
    private TableColumn<Book, Integer> booktypecol;
    @FXML
    private TableColumn<Book, Boolean> availablecol;
    @FXML
    private TextField BookTitle;
    @FXML
    private ListView<String> listviewsearch;


    public void initialize() throws IOException {
        //anchorPane.setMaxSize();

        DBConnection.getInstance();


    }


    DBConnection dbhandler = new DBConnection();


    public void SearchBook() throws IOException {
        // clearBookSearch();
        ObservableList<String> List = FXCollections.observableArrayList();
        String Title = BookTitle.getText();
        String qu = "SELECT * FROM book WHERE title LIKE '%" + Title + "%';";
        ResultSet result = dbhandler.ExecuteQuery(qu);
        List.add("Book information");
        boolean value = false;
        try {
            while (result.next()) {
                //Adding the columns i am going to use
                String bookTitle = Title;


                int ISBN = result.getInt("isbn");
//                    String Author = result.getString("author");
                Boolean Available = result.getBoolean("available");
                List.add(" BOOk Title " + bookTitle);
                List.add("Book ISBN:" + ISBN);
                List.add("Book Author : " + result.getString("author"));
                String Status = (Available) ? "Available" : "Not Available";
                List.add("Available :" + Status);

                // adding the columns to my javafx
//                    isbncol.setText(String.valueOf(ISBN));
//                    authorcol.setText(Author);
//                    // this one changes depenting on if the book was issued or not
//                    String status = (Available) ? "Available" :  "Not Available";
//                    availablecol.setText(status);


                value = true;
            }

            if (errorHandling(value)) return;


        } catch (Exception ex) {
            ex.printStackTrace();


        }


        listviewsearch.getItems().setAll(List);

    }

    static boolean errorHandling(boolean value) {
        if (value == false) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Error, Couldn't find any book with that name!");
            alert.showAndWait();
            return true;
        }
        return false;
    }

    public void homePage(ActionEvent event) throws IOException {

        Parent list = FXMLLoader.load(getClass().getResource("/Home/home.fxml"));
        Scene scene = new Scene(list);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Home page");
        window.setScene(scene);
        window.show();
    }
    public void reserve() throws SQLException {

        String qu = "SELECT * FROM book WHERE title = '" + BookTitle.getText() + "'";
        ResultSet result = dbhandler.ExecuteQuery(qu);
        Calendar currenttime = Calendar.getInstance();
        Date sqldatenow = new Date((currenttime.getTime()).getTime());
        currenttime.add(Calendar.DAY_OF_MONTH, 3);
        Date sqldate = new Date((currenttime.getTime()).getTime());


        while (result.next()) {

            if (SceneData.getInstance().getLoggedInMember() == null){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please log in", ButtonType.OK);
                alert.showAndWait();
                return;
            }

            if (result.getDate("reserved_until") == null && SceneData.getInstance().getLoggedInMember() != null) {
                String qu2 = "UPDATE book " +
                        "SET reserved_by = " +
                        "(SELECT SSN FROM member WHERE SSN = '" + SceneData.getInstance().getLoggedInMember().getMember_SSN() + "')" +
                        ",reserved_until = '" + sqldate + "';";
                dbhandler.executeAction(qu2);
            } else if (result.getDate("reserved_until") != null);{
                if( result.getDate("reserved_until").after(sqldatenow)){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Book is already reserved? pleased try again");
                    alert.showAndWait();
                }
            }  if (SceneData.getInstance().getLoggedInMember() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Please log in before reserving!");
                alert.showAndWait();
            }
        }
    }


}