package Login;

import DataBase.DBConnection;
import Search.BookListController;
import Search.BorrowBook;
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

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UserInfoController implements Initializable {
    @FXML
    private ScrollPane borrowBookScrollPane;
    @FXML
    private Label name;
    @FXML
    private Label email;

    List<BorrowBook> borrowSelection;
    GridPane gridPane;
    DBConnection db = new DBConnection();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        name.setText(LoginController.name);
        email.setText(DBConnection.getEmail());
        borrowSelection = BookListController.myBorrowedBooks;
        System.out.println(DBConnection.getUserId());
        try {
            for (int i=0;i<borrowSelection.size();i++){
                System.out.println(borrowSelection.get(i));
                System.out.println(borrowSelection.get(i).getTitle());

                db.uppdateQuery(DBConnection.getUserId(),
                        borrowSelection.get(i).getIsbn(),
                        borrowSelection.get(i).getActualReturnDate(),
                        borrowSelection.get(i).getActualBorrowDate());

                db.updateAvilablity(borrowSelection.get(i).getIsbn(),false);

            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error in updating borrow");
        }
        if (gridPane != null) {

        }
        gridPane = new GridPane();
        gridPane.setHgap(40);
        gridPane.setVgap(20);
        gridPane.add(new Label("  Title"), 0, 0);
        gridPane.add(new Label("  Borrow date"), 1, 0);
        gridPane.add(new Label(" Return date"), 2, 0);

        for (int i = 0; i < borrowSelection.size(); i++) {
            gridPane.add(new Label(borrowSelection.get(i).getTitle()), 0, i + 1);
            gridPane.add(new Label(borrowSelection.get(i).getBorrowDate()), 1, i + 1);
            gridPane.add(new Label(borrowSelection.get(i).getReturnDate()), 2, i + 1);


        }

            borrowBookScrollPane.setContent(gridPane);

        // Update Database



    }



    public void logOut(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        //loader.setLocation(getClass().getResource("BookList.fxml"));
        Parent bView = loader.load(getClass().getResource("/home/home.fxml"));
        Scene scene = new Scene(bView);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }
    }

