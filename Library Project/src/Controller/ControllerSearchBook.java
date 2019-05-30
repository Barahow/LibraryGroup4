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

   import java.io.IOException;
   import java.sql.ResultSet;
   import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
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


        boolean condi;
        boolean brrcon = false;
        int count = 0;

        DBConnection dbhandler = new DBConnection();



        public void SearchBook() throws IOException {
           // clearBookSearch();
            ObservableList<String> List = FXCollections.observableArrayList();
            String Title = BookTitle.getText();
            String qu = "SELECT * FROM book WHERE title = '" + Title + "'";
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

                if (value == false) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Error, Couldn't find any book with that name!");
                    alert.showAndWait();
                    return;
                }


            } catch (Exception ex) {
                ex.printStackTrace();


            }



            listviewsearch.getItems().setAll(List);

        }

        public void homePage(ActionEvent event) throws IOException {

            Parent list = FXMLLoader.load(getClass().getResource("/Home/home.fxml"));
            Scene scene = new Scene(list);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setTitle("Home page");
            window.setScene(scene);
            window.show();
        }


        public void borrowInfo(String title) {
            //Calendar cl = Calendar.getInstance();
            DateFormat dateFormat = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss");
            Date date = new Date();
            LocalDateTime localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

            localDate = localDate.plusDays(10);
            Date returndate = Date.from(localDate.atZone(ZoneId.systemDefault()).toInstant());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("*********** Borrowed books ********\n" + "Book Tiltle: " + title +
                    "\nBorrowed date:  " + dateFormat.format(date) +
                    "\nReturn Date:    " + dateFormat.format(returndate));
            alert.setTitle("Borrow Info");

            //alert.setHeaderText("Woopppps");
            //alert.showAndWait();
            alert.show();

        }

        public static void borrAlert(String infoMessage, String headerText, String title) {


            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText(infoMessage);
            alert.setTitle(title);
            alert.setHeaderText("Woopppps");
            //alert.showAndWait();
            alert.show();
        }

//        public void clearBookSearch() {
//
//           listviewsearch.setEditable(false);
//
//            isbncol.setText("");
//            authorcol.setText("");
//            availablecol.setText("");
//
//        }
    }

        // this method is used to set my javafx columns
    /*    private void colum() {
            titlecol.setCellValueFactory(new PropertyValueFactory<>("Title"));
            isbncol.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
            authorcol.setCellValueFactory(new PropertyValueFactory<>("Author"));
            booktypecol.setCellValueFactory(new PropertyValueFactory<>("BookType"));
            availablecol.setCellValueFactory(new PropertyValueFactory<>("availiability"));
        }


    }*/
