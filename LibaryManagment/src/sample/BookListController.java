package Search;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


public class BookListController implements Initializable {

    private Book mybook;
    @FXML
    private Button home;
    @FXML
    private TableView<Book> tableView;
    @FXML
    private TableColumn<Book, String> tableisbn;
    @FXML
    private TableColumn<Book, String> tabletitle;
    @FXML
    private AnchorPane anchorPaneSearch;
    ObservableList<Book> oblist = FXCollections.observableArrayList();

    @FXML
    public void backHome(ActionEvent event) throws Exception {

   /* Book b = new Book("12345","ABNNmm");
    oblist.add(b);
    isbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
    title.setCellValueFactory(new PropertyValueFactory<>("title"));
    table.setItems(oblist);*/

   // change to back home

        Parent list = FXMLLoader.load(getClass().getResource("Search.fxml"));
        Scene scene = new Scene(list);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        //scene.getStylesheets().add(getClass().getResource("LMS.css").toExternalForm());
        window.setTitle("Login");
        window.setScene(scene);
        window.show();

    }

    @FXML
    public void display(ArrayList<Book> myBook) {
        oblist = (ObservableList<Book>) myBook;
        tableisbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        tabletitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        tabletitle.setCellValueFactory(new PropertyValueFactory<>("author"));
        tabletitle.setCellValueFactory(new PropertyValueFactory<>("catagory"));
        tabletitle.setCellValueFactory(new PropertyValueFactory<>("Availability"));
        tableView.setItems(oblist);

    }

 /*   public void initData(ObservableList<Book> ol) {
        oblist = ol;
        isbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        table.setItems(oblist);

    }*/

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*isbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        table.setItems(oblist);*/
        if (ProjectManager.getInstance().gridPane != null) {
            anchorPaneSearch.getChildren().add(ProjectManager.getInstance().gridPane);
        }

    }

}
