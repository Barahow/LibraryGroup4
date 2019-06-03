package Search;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SearchCatalog implements Initializable {

    @FXML
    private TextField search1;
    @FXML
    private TextArea view;
    @FXML private Button show;
    @FXML private ListView list;
    @FXML private TextField ex;
    boolean condi;
    int count = 0;

    DBConnection conn = new DBConnection();
    ArrayList<Book> mylist;

   /* @FXML
    public void searchBook(ActionEvent event) throws SQLException {
        conn.dbConnection();
        conn.getBooks(se.getText());

        conn.dbDisconnect();

    }*/
    public void changeBookList(ActionEvent event) throws IOException,SQLException{
        condi = true;
        if (condi){
            mylist = null;
            search1.setPromptText("Enter title");
            //view.setText("");
            conn.dbConnection();
            //mylist = FXCollections.observableArrayList();
            mylist = conn.executeQuery(search1.getText());


            GridPane gridPane = new GridPane();
            gridPane.add( new TextField("ISBN"), 0,0);
            gridPane.add(new TextField("Title"), 1,0);
            gridPane.add(new TextField("Author"), 2,0);
            gridPane.add(new TextField("Availability"),3,0);
            for (int i = 0; i < mylist.size() ; i++) {
                gridPane.add(new TextField(mylist.get(i).isbn), 0,i+1);
                gridPane.add(new TextField(mylist.get(i).title), 1,i+1);
                gridPane.add(new TextField(mylist.get(i).author), 2,i+1);
                gridPane.add(new TextField(mylist.get(i).availability),3,i+1);

            }

            ProjectManager.getInstance().setGridPane(gridPane);


            //bc.display(list.setItems((ObservableList) mylist));

           /* for (int i=0;i<mylist.size();i++){
                Book myb = mylist.get(i);
                view.setText(myb.getIsbn() +"    " + myb.getTitle());
                //view.appendText((myb.getIsbn() +"    " + myb.getTitle()+"\n"));
            }*/

            BookListController c = new BookListController();
            for (int i=0;i<mylist.size();i++){
                c.oblist.add(mylist.get(i));
            }


            condi =false;

            search1.setPromptText("Enter title");

        }

        FXMLLoader loader = new FXMLLoader();
        //loader.setLocation(getClass().getResource("BookList.fxml"));
        Parent list = loader.load(getClass().getResource("BookList.fxml"));
        Scene scene = new Scene(list);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        //scene.getStylesheets().add(getClass().getResource("LMS.css").toExternalForm());
        window.setTitle("Home Page");
        window.setScene(scene);

        window.show();



    }



 /*  @FXML public void display(ActionEvent event){
        textf1.setText(str);
        System.out.println(str);
        System.out.println("Executed");
    }*/

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        search1.setPromptText("Enter title");
    }
}
