package Search;

import DataBase.DBConnection;
import Home.DataManager;
import Search.View.ReservedBook;
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
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;


public class BookListController implements Initializable {

    private Book mybook;
    @FXML
    private Button home;
    @FXML
    private AnchorPane barrowAnchor;
    @FXML
    private ScrollPane borrowBookScrollPane;
    @FXML
    private AnchorPane anchorPaneSearch;
    @FXML
    private TableView<Book> table;
    @FXML
    private TableColumn<Book, Integer> isbnCol;
    @FXML
    private TableColumn<Book, String> titleCol;
    @FXML
    private TableColumn<Book, String> authorCol;
    @FXML
    private TableColumn<Book, Boolean> availableCol;
    @FXML
    private TableColumn<Book, Boolean> reserveCol;
    @FXML
    private TableColumn<Book, CheckBox> selectCol;
    @FXML
    private TableColumn<Book, Integer> booktypecol;
    @FXML
    private TextArea tx;
    public static boolean borrowHistory;



    List<Book> books;
    public static List<BorrowBook> myBorrowedBooks = new ArrayList<>();
    public static List<ReservedBook> myReservedBooks = new ArrayList<>();
    ObservableList<Book> oblist = FXCollections.observableArrayList();
    private GridPane gridPane;
    public static  boolean reserv_controll=false;
    DBConnection db = new DBConnection();


    // change to back home
    @FXML
    public void backHome(ActionEvent event) throws Exception {
        Parent list = FXMLLoader.load(getClass().getResource("/view/search.fxml"));
        Scene scene = new Scene(list);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Login");
        window.setScene(scene);
        window.show();

    }

    public void displayAllBooks() {
        for (int i = 0; i < books.size(); i++) {
            CheckBox ch = new CheckBox();
            books.get(i).setCheckBox(ch);
        }

        ObservableList<Book> obBarrowedlist = FXCollections.observableArrayList(books);

        isbnCol.setCellValueFactory(new PropertyValueFactory<Book, Integer>("isbn"));
        titleCol.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
        authorCol.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
        booktypecol.setCellValueFactory(new PropertyValueFactory<Book, Integer>("bookType"));
        availableCol.setCellValueFactory(new PropertyValueFactory<Book, Boolean>("availability"));
        selectCol.setCellValueFactory(new PropertyValueFactory<Book, CheckBox>("checkBox"));
        reserveCol.setCellValueFactory(new PropertyValueFactory<Book, Boolean>("reserved"));

        table.setItems(obBarrowedlist);

        // table.setItems(oblist);

/*        isbnCol.setCellValueFactory(isbn -> isbn.getValue().isbnProperty());
        titleCol.setCellValueFactory(title -> title.getValue().titleProperty());
        authorCol.setCellValueFactory(title -> title.getValue().authorProperty());
        availableCol.setCellValueFactory(available -> available.getValue().availabilityProperty());
        selectCol.setCellValueFactory(checkBox -> checkBox.getValue().getCheckBox();*/


/*        table.setRowFactory(tv -> {
            TableRow<Book> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Book rowData = row.getItem();
                    BorrowBook borrowBook =
                            new BorrowBook(rowData.getIsbn(), rowData.getTitle(), rowData.getAuthor(),
                                    rowData.isAvailability(), getCurrentDate().toString(),
                                    calculateReturnDate().toString());
                    myBorrowedBooks.add(borrowBook);
                    //buildBorrowList();

                    System.out.println(rowData.getTitle());
                }
            });
            return row;
        });*/
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DBConnection dbConn = new DBConnection();

        //SearchCatalog sC = new SearchCatalog();
        String title = SearchCatalog.booktitle;

        try {
            dbConn.dbConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // books = dbConn.fetchAllBooks();  // This fetches all books
        books = dbConn.getBooks(title);

        if (ProjectManager.getInstance().gridPane != null) {
            anchorPaneSearch.getChildren().add(ProjectManager.getInstance().gridPane);
        }

        displayAllBooks();
    }


    // build grid pane on run for borrowed books
 /*   private void buildBorrowList() {

        if (gridPane != null) {

        }
        gridPane = new GridPane();
        gridPane.setHgap(40);
        gridPane.setVgap(20);
        gridPane.add(new Label("  Title"), 0, 0);
        //gridPane.add(new Label("  Borrow date"), 1, 0);
        //gridPane.add(new Label(" Return date"), 2, 0);

        for (int i = 0; i < myBorrowedBooks.size(); i++) {
            gridPane.add(new Label(myBorrowedBooks.get(i).getTitle()), 0, i + 1);
           // gridPane.add(new Label(myBorrowedBooks.get(i).getBorrowDate()), 1, i + 1);
            //gridPane.add(new Label(myBorrowedBooks.get(i).getReturnDate()), 2, i + 1);

        }
        borrowBookScrollPane.setContent(gridPane);
    }*/


    // return date after 10 days
    private Date calculateReturnDate() {
        Calendar currenttime = Calendar.getInstance();
        currenttime.add(Calendar.DAY_OF_MONTH, 10);
        Date sqldate = new Date((currenttime.getTime()).getTime());
        return sqldate;
    }

    //borrow date at current time
    private Date getCurrentDate() {

        Calendar currenttime = Calendar.getInstance();
        Date sqldate = new Date((currenttime.getTime()).getTime());
        System.out.println("current date: " + sqldate.toString());

        //LocalDateTime localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return sqldate;
    }

    @FXML
    public void signInPage(ActionEvent event) throws Exception {
        for (int i = 0; i < table.getItems().size(); i++) {
            Book book = table.getItems().get(i);
            if (book.getCheckBox().isSelected() && book.isAvailability()) {
                BorrowBook borrowBook =
                        new BorrowBook(
                                book.getIsbn(),
                                book.getTitle(),
                                book.getAuthor(),
                                book.getBookType(),
                                book.isAvailability(),
                                false,
                                getCurrentDate().toString(),
                                calculateReturnDate().toString(), "");
                myBorrowedBooks.add(borrowBook);

            }

        }

        borrowHistory = true;
        Parent list = FXMLLoader.load(getClass().getResource("/view/signIn.fxml"));
        Scene scene = new Scene(list);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("SignInPage");
        window.setScene(scene);
        window.show();

    }

    public void reserveBook(ActionEvent event) {
        if (DataManager.getInstance().getLoggedInUser() == null) {

            Alert alert = new Alert(Alert.AlertType.ERROR, "User not logged in", ButtonType.OK);
            alert.showAndWait();

            //TO sign in.
            reserv_controll = true;

            return;
        }

        StringBuilder st = new StringBuilder();

        for (Book book : books) {
            if (!book.isAvailability() && book.getCheckBox().isSelected()) {

                if (book instanceof BorrowBook) {
                    ReservedBook  r_book = new ReservedBook(book.getIsbn(), book.getTitle(), book.getAuthor(), book.getBookType(),
                            book.isAvailability(), true, getCurrentDate().toString(), ((BorrowBook) book).getReturnDate());
                    db.reserveBook(r_book);
                    //db.reserveBook((new ReservedBook(book.getIsbn(), book.getTitle(), book.getAuthor(), book.getBookType(),
                    //book.isAvailability(), true, getCurrentDate().toString(), ((BorrowBook) book).getReturnDate())));

                    db.updateReservation(book.getIsbn(),true);
                    myReservedBooks.add(r_book);
                    st.append(book.getTitle() + ", ");
                }
            }
        }
        st.append( " successfully reserved");
        reserv_controll = false;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, st.toString(), ButtonType.OK);
        alert.showAndWait();

    }

}
