package Login;

import DataBase.DBConnection;
import Home.DataManager;
import Search.BookListController;
import Search.BorrowBook;
import Search.View.ReservedBook;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

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
    @FXML
    private Button pdf;

    ArrayList<BorrowBook> borrowSelection;
    ArrayList<ReservedBook> reserveSelection;

    GridPane gridPane;
    DBConnection db = new DBConnection();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        name.setText(LoginController.name);
        email.setText(DBConnection.getEmail());
        System.out.println("##### " + DBConnection.getUserId());


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

        if (BookListController.reserv_controll){
            reserveSelection = (ArrayList)BookListController.myReservedBooks;
            for (int i=0;i<reserveSelection.size();i++){
                db.reserveBook(reserveSelection.get(i));
                db.updateReservation(reserveSelection.get(i).getIsbn(),true);
            }

        }else if (!BookListController.reserv_controll){
            //Bring it from database.
            reserveSelection =db.get_Reservation(DBConnection.getUserId());

        }


        //  *********** Second part ******************
        if (gridPane != null) {

        }
        gridPane = new GridPane();
        gridPane.setHgap(40);
        gridPane.setVgap(20);
        Label x = new Label("  Title");
        Label x1 = new Label("  Book Title ");

        x.setStyle("-fx-text-fill:GREEN");
        x1.setStyle("-fx-text-fill:BLUE");

        Label y = new Label("  Borrow date");
        Label Y1 = new Label(" Reservation Date");
        //y.setStyle("-fx-font-weight: bold");
        y.setStyle("-fx-text-fill:GREEN");
        Y1.setStyle("-fx-text-fill:BLUE");


        Label z = new Label("  Return date");
        Label z1 = new Label(" Available  date");
        z.setStyle("-fx-text-fill:GREEN");
        z1.setStyle("-fx-text-fill:BLUE");

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


        if (reserveSelection!= null) {
            gridPane.add(new Label("*********"), 0, borrowSelection.size() +1);
            gridPane.add(new Label("*********"), 1, borrowSelection.size() +1);
            gridPane.add(new Label("*********"), 2, borrowSelection.size() +1);
            gridPane.add(new Label("*********"), 3, borrowSelection.size() +1);

            //  Reservation Gridepane title

            gridPane.add(x1, 0, borrowSelection.size() +2);
            gridPane.add(Y1, 1, borrowSelection.size() +2);
            gridPane.add(z1, 3, borrowSelection.size() +2);
            for (int i = 0; i < reserveSelection.size(); i++) {

                gridPane.add(new Label(reserveSelection.get(i).getTitle()), 0, borrowSelection.size() + i+3);
                gridPane.add(new Label(reserveSelection.get(i).getReservationDate()), 1, borrowSelection.size() + i+3);
                gridPane.add(new Label(reserveSelection.get(i).getAvilabledate()), 3, borrowSelection.size() + i+3);

            }
        }else{
            System.out.println(" ***** NO Reserved book found *****");
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

    }

