package Controller;


   import Model.Book;
   import com.mysql.cj.protocol.Resultset;
   import javafx.collections.FXCollections;
   import javafx.collections.ObservableList;
   import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
   import javafx.scene.control.*;
   import javafx.scene.control.cell.PropertyValueFactory;
   import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
   import sample.DbUtil;

   import java.io.IOException;
import java.net.URL;
   import java.sql.ResultSet;
   import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

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
        private TextField txtsearch;


        public void initialize() throws IOException {
            //anchorPane.setMaxSize();

            DbUtil.getInstance();


        }


        boolean condi;
        boolean brrcon = false;
        int count = 0;

        DbUtil dbhandler = new DbUtil();

        ObservableList<Book> List = FXCollections.observableArrayList();

        public void SearchBook() throws IOException {
            clearBookSearch();
            String Title = txtsearch.getText();
            String qu = "SELECT * FROM book WHERE title = '" + Title + "'";
            ResultSet result = dbhandler.ExecuteQuery(qu);
            boolean value = false;
            try {
                while (result.next()) {
                    int ISBN = result.getInt("isbn");
                    String Author = result.getString("author");
                    Boolean Available = result.getBoolean("available");

                    isbncol.setText(String.valueOf(ISBN));
                    authorcol.setText(Author);
                    availablecol.setText(String.valueOf(Available));


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


            //ArrayList<BorrowBook> myBorrowedBooks = new ArrayList<>();

//
//        public void SearchBook (ActionEvent event) throws IOException, SQLException {
//
//
//
//            Boolean result = DbUtil.getInstance().deletemember(Search);
//            // If you havent selected a book to dleete you get an error
//            if (Search == null) {
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setHeaderText(null);
//                alert.setContentText("Couldn't find any book!");
//                alert.showAndWait();
//                return;
//            }
//
//            //            condi = true;
////
////            if (condi) {
////                List= null;
////                search1.setPromptText("Enter title");
////                dbhandler.dbConnection();
////                List = dbhandler.ExecuteQuery(search1.getText());
////                booktitle = search1.getText();
//
//
//
//
//
//
///*
//
//            GridPane gridPane = new GridPane();
//            gridPane.setHgap(40);
//            gridPane.setVgap(20);
//            gridPane.add(new Label("  ISBN"), 0, 0);
//            gridPane.add(new Label(" Title"), 1, 0);
//            gridPane.add(new Label(" Author"), 2, 0);
//            gridPane.add(new Label(" Availability"), 3, 0);
//            TextField textField = new TextField();
//
//            */
///**
// * This is for holding current date and add 10 to it.
// *//*
//
//            DateFormat dateFormat = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss");
//            Date date = new Date();
//            LocalDateTime localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
//
//            localDate = localDate.plusDays(10);
//            Date returndate = Date.from(localDate.atZone(ZoneId.systemDefault()).toInstant());
//
//            */
///**
// * ###########################################################
// *
// *//*
//
//
//            for (int i = 0; i < myBooks.size(); i++) {
//                gridPane.add(new Label(myBooks.get(i).isbn), 0, i + 1);
//                gridPane.add(new Label(myBooks.get(i).title), 1, i + 1);
//                gridPane.add(new Label(myBooks.get(i).author), 2, i + 1);
//                //gridPane.add(new Label(myBooks.get(i).availability), 2, i + 1);
//
//                Button button;
//                if (myBooks.get(i).availability) {
//                    button = new Button("available");
//                    //button.setOnAction(event1 -> button.setDisable(true));
//
//                    count = i;
//                    button.setOnAction(new EventHandler<ActionEvent>() {
//                        @Override
//                        public void handle(ActionEvent event) {
//                            borrowInfo(myBooks.get(count).title);
//                            button.setText("Borrowed");
//                            button.setDisable(true);
//                            brrcon = true;
//
//
//                            try {
//                                conn.uppdateQuery(myBooks.get(count).getIsbn());
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                            BorrowBook br = new BorrowBook(myBooks.get(count).title,
//                                    dateFormat.format(date),
//                                    dateFormat.format(returndate));
//                            myBorrowedBooks.add(br);
//                            count--;
//                            ProjectManager.getInstance().setBorrowBookList(myBorrowedBooks);
//                        }
//
//                    });
//                    gridPane.add(button, 3, i + 1);
//
//                } else {
//                    button = new Button("unavailable");
//                    button.setOnAction((event1) -> button.setDisable(true));
//                    gridPane.add(button, 3, i + 1);
//                }
//
//            }
//
//
//            ProjectManager.getInstance().setGridPane(gridPane);
//
//            BookListController c = new BookListController();
//
//            for (int i = 0; i < myBooks.size(); i++) {
//                c.oblist.add(myBooks.get(i));
//            }
//
//            for (int j = 0; j < myBorrowedBooks.size(); j++) {
//                c.borrowedBookList.add(myBorrowedBooks.get(j));
//            }
//
//            condi = false;
//
//            search1.setPromptText("Enter title");
//
//*/
//                condi=false;
//            }

            FXMLLoader loader = new FXMLLoader();
            //loader.setLocation(getClass().getResource("BookList.fxml"));
            Parent bView = loader.load(getClass().getResource("../View/Member_List.fxml"));
            Scene scene = new Scene(bView);

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

        public void clearBookSearch() {
            isbncol.setText("");
            authorcol.setText("");
            availablecol.setText("");

        }
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
