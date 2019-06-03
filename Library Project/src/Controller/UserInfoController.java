package Controller;



import com.itextpdf.text.DocumentException;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import model.BorrowBook;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.DataManager;
import model.PDFPrinter;
import sample.DBConnection;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UserInfoController implements Initializable {


    @FXML
    private ScrollPane borrowBookScrollPane;
    @FXML
    private Label name;
    @FXML
    private Label email;
    @FXML
    private Button logoutButton;

    ArrayList<BorrowBook> borrowSelection;
    GridPane gridPane;
    DBConnection db = new DBConnection();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        name.setText(LoginController.name);
        email.setText(DBConnection.getEmail());


        if (BookListController.borrowHistory) {
            borrowSelection = (ArrayList) BookListController.myBorrowedBooks;
            System.out.println("borrowSelection: " + borrowSelection);
            System.out.println(DBConnection.getUserId());

            try {
                for (int i = 0; i < borrowSelection.size(); i++) {
                    System.out.println(borrowSelection.get(i));
                    System.out.println(borrowSelection.get(i).getTitle());

                    db.uppdateQuery(DBConnection.getUserId(),
                            borrowSelection.get(i).getIsbn(),
                            borrowSelection.get(i).getActualReturnDate(),
                            borrowSelection.get(i).getActualBorrowDate());

                    db.updateAvilablity(borrowSelection.get(i).getIsbn(), false);
                    System.out.println("Title of first borrowed book" + borrowSelection.get(i).getTitle());
                    // DataManager.getInstance().getLoggedInUser().setBorrowedBooks(borrowSelection);

                }


                //borrowSelection = new ArrayList<>();


                System.out.println(DataManager.getInstance().getLoggedInUser());
                System.out.println(borrowSelection);
                DataManager.getInstance().getLoggedInUser().setBorrowedBooks(borrowSelection);


            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error in updating borrow");
            }

        } else if (!BookListController.borrowHistory) {


            borrowSelection = db.get_Borrowed_History(DBConnection.getUserId());


        }
        //  *********** Second part ******************
        if (gridPane != null) {

        }
        gridPane = new GridPane();
        gridPane.setHgap(40);
        gridPane.setVgap(20);
        Label x = new Label("  Title");
        x.setStyle("-fx-text-fill:BLUE");

        Label y = new Label("  Borrow date");
        //y.setStyle("-fx-font-weight: bold");
        y.setStyle("-fx-text-fill:BLUE");


        Label z = new Label("  Return date");
        z.setStyle("-fx-text-fill:RED");

        gridPane.add(x, 0, 0);
        gridPane.add(y, 1, 0);
        gridPane.add(z, 3, 0);
        //gridPane.add(new Label("  Due date"), 3, 0);
        //gridPane.add(new Label("  Return a book"), 3, 0);


        for (int i = 0; i < borrowSelection.size(); i++) {
            //Button button;
            gridPane.add(new Label(borrowSelection.get(i).getTitle()), 0, i + 1);
            gridPane.add(new Label(borrowSelection.get(i).getBorrowDate()), 1, i + 1);
            //gridPane.add(new Label(borrowSelection.get(i).getDueDate()), 2, i + 1);
            gridPane.add(new Label(borrowSelection.get(i).getReturnDate()), 3, i + 1);
            //gridPane.add(new Label(borrowSelection.get(i).getDueDate()), 3, i + 1);


        }

        /*if (borrowSelection.get(i).getReturnDate().equals("not returned")) {
                button = new Button("Return");
                gridPane.add(new Label(borrowSelection.get(i).getReturnDate()), 3, i + 1);
                gridPane.add(button, 3, i + 1);
                button.setUserData(borrowSelection.get(i));

                button.setOnMouseClicked(event -> {
                    db = new DBConnection();
                    button.setDisable(true);
                    BorrowBook borrowBook = (BorrowBook) button.getUserData();
                    // make book returned in db;
                    db.returnBook(borrowBook);
                    System.out.println("Book returned");
                    //buildBorrowList();
                    try {
                        db.dbDisconnect();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
            } else {
                button = new Button("Returned");
                button.setDisable(true);
                //gridPane.add(new Label(borrowSelection.get(i).getReturnDate()), 3, i + 1);
                gridPane.add(button, 3, i + 1);
            }
        }*/

        borrowBookScrollPane.setContent(gridPane);
        // Update Database
    }


    public void logOut(ActionEvent event) throws IOException {
        Stage window = (Stage) logoutButton.getScene().getWindow();
        window.close();
        try {
            db.dbDisconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
  /*      FXMLLoader loader = new FXMLLoader();
        //loader.setLocation(getClass().getResource("BookList.fxml"));
        Parent bView = loader.load(getClass().getResource("/view/StartMenu.fxml"));
        Scene scene = new Scene(bView);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();*/
    }




    private void openPDFRecipeSaver(ActionEvent event, ArrayList<BorrowBook> books) throws IOException, DocumentException {
        Node source = (Node) event.getSource();
        Window theStage = source.getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
        fileChooser.setTitle("Save recipe");
        File file = fileChooser.showSaveDialog(theStage);

        if (file != null) {
            PDFPrinter print = new PDFPrinter();
            print.createBorrowedBookPDF(file.getAbsolutePath(), books);
        }
    }

    public void savePdf(ActionEvent event) {

        try {
            System.out.println("Current user: " + DataManager.getInstance().getLoggedInUser());
            if (DataManager.getInstance().getLoggedInUser().getBorrowedBooks() != null) {
                openPDFRecipeSaver(event, DataManager.getInstance().getLoggedInUser().getBorrowedBooks());
            } else {
                System.out.println("This user did not have any borrowed books.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }



    public void loadMenu(String location, String title) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(location));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
    public void loadSearchBook() {
            loadMenu("../view/SearchBook.fxml", "Search Book");
        }


    public void  loadIssueBook() {
        loadMenu("../view/IssueBook.fxml", "Issue Book");
    }

    public void loadBookCatalog() {
        loadMenu("../view/BookCategory.fxml", "Book Catalog");

    }

    public void loadBookView() {
        loadMenu("../view/Book_List.fxml", "Book List");


    }

public void loadReturnBook() {
    loadMenu("../View/ListOfIssues.fxml","Return Book");
}
    }







