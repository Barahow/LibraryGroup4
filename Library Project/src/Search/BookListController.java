package Search;

import DataBase.DBConnection;
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
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;


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
    private TableColumn<Book, String> isbnCol;
    @FXML
    private TableColumn<Book, String> titleCol;
    @FXML
    private TableColumn<Book, String> authorCol;
    @FXML
    private TableColumn<Book, Boolean> availableCol;
    @FXML
    private TableColumn<Book, CheckBox> selectCol;
    @FXML
    private TextArea tx;

    List<Book> books;
    public static List<BorrowBook> myBorrowedBooks = new ArrayList<>();
    ObservableList<Book> oblist = FXCollections.observableArrayList();
    private GridPane gridPane;
    DBConnection db = new DBConnection();

    // change to back home
    @FXML
    public void backHome(ActionEvent event) throws Exception {
        Parent list = FXMLLoader.load(getClass().getResource("Search.fxml"));
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
        isbnCol.setCellValueFactory(new PropertyValueFactory<Book, String>("isbn"));
        titleCol.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
        authorCol.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));
        availableCol.setCellValueFactory(new PropertyValueFactory<Book, Boolean>("availability"));
        selectCol.setCellValueFactory(new PropertyValueFactory<Book, CheckBox>("checkBox"));

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
        books = dbConn.executeQuery(title);

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
        Date date = new Date();
        LocalDateTime localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        localDate = localDate.plusDays(10);
        return Date.from(localDate.atZone(ZoneId.systemDefault()).toInstant());
    }

    //borrow date at current time
    private Date getCurrentDate() {
        Date date = new Date();
        LocalDateTime localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return Date.from(localDate.atZone(ZoneId.systemDefault()).toInstant());
    }

    @FXML
    public void signInPage(ActionEvent event) throws Exception {
        for (int i = 0; i < table.getItems().size(); i++) {

            if (table.getItems().get(i).getCheckBox().isSelected()) {
                BorrowBook borrowBook =
                        new BorrowBook(table.getItems().get(i).getIsbn(),
                                table.getItems().get(i).getTitle(),
                                table.getItems().get(i).getAuthor(),
                                table.getItems().get(i).isAvailability(),
                                getCurrentDate().toString(),
                                calculateReturnDate().toString());
                myBorrowedBooks.add(borrowBook);

            }

        }


        Parent list = FXMLLoader.load(getClass().getResource("/Login/signIn.fxml"));
        Scene scene = new Scene(list);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("SignInPage");
        window.setScene(scene);
        window.show();

    }


}
