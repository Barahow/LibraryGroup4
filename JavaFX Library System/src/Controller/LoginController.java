package Controller;


import model.BorrowBook;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import sample.AbstractController;
import sample.DBConnection;


import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class LoginController extends AbstractController implements Initializable {
    @FXML
    private TextField usernamem;

    @FXML
    private PasswordField passm;

    @FXML
    private Button signupm;

    @FXML
    private Button signinm;
@FXML private Button backhomebutton;
    @FXML
    private Label forgotpassm;

    @FXML
    private TextField usernamea;

    @FXML
    private PasswordField passa;

    @FXML
    private Button signupa;

    @FXML
    private Button signina;

    @FXML
    private Label forgotpassa;
    @FXML
    private TextField mytext;

    @FXML
    private Label close;
    @FXML
    private Label label;
    @FXML
    private ScrollPane anchorpin;
    static String name;

    Stage dialogStage = new Stage();
    Scene scene;
    private GridPane gridPane;
    public static List<BorrowBook> myBorrowedBooks = new ArrayList<>();

    PreparedStatement preparedStatement = null;
    Statement statement = null;
    ResultSet resultSet = null;

    private DBConnection dbConnection = null;
    private int count = 0;


    public void signIn(ActionEvent event) {
        String um;
        String pm;
        um = usernamem.getText();
        pm = passm.getText();
        name = um;
        dbConnection = new DBConnection();
        //long time = System.nanoTime();
        //label.setDisable(true);

        try {
            if (dbConnection.signIn(um, pm)) {
                if (dbConnection.getAdmin()) {
                    FXMLLoader loader = new FXMLLoader();
                    //loader.setLocation(getClass().getResource("BookList.fxml"));
                    Parent bView = loader.load(getClass().getResource("/view/AdminMenu.fxml"));
                    Scene scene = new Scene(bView);
                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    window.setScene(scene);
                    window.show();
                }
                if (!dbConnection.getAdmin()) {
                    FXMLLoader loader = new FXMLLoader();
                    //loader.setLocation(getClass().getResource("BookList.fxml"));
                    Parent bView = loader.load(getClass().getResource("../view/userInfo.fxml"));
                    Scene scene = new Scene(bView);
                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    window.setScene(scene);
                    window.show();
                }


            } else {
                label.setText("OBs wrong!!");
                infoBox("Please enter the correct username and Password",
                        null, "Failed");
                count++;
                usernamem.clear();
                passm.clear();
                if (count >= 3) {
                    infoBox("Please try after 15 minutes!!", null, "Failed");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        //System.out.println("Please try after 15 minutes!!");
        //infoBox("Please try after 15 minutes!!", null, "Failed");
    }


    /*public void handleButoonAction(ActionEvent event) throws Exception {
        if (event.getSource() == close) {
            System.exit(0);
        }
    }*/

   /* @FXML
    public void signIn(ActionEvent event) throws Exception {
       String um = usernamem.getText();
       String pm = passm.getText();
       String sqlMem = "SELECT * FROM login;";
       System.out.println("i am here");
       try {
           Statement statement = connection.createStatement();
           //preparedStatement.setString(1, um);
           //preparedStatement.setString(2, pm);
           resultSet = statement.getBooks(sqlMem);

           while(resultSet.next()){
               if(um.equals(resultSet.getString("username"))){
                   System.out.println("Username was correct!");
                   if(pm.equals(resultSet.getString("passw"))){
                       infoBox("Login Successfull", null, "Success");
                       Node node = (Node) event.getSource();
                       dialogStage = (Stage) node.getScene().getWindow();
                       dialogStage.close();
                       scene = new Scene(FXMLLoader.load(getClass().getResource("userInfo.fxml")));
                       dialogStage.setScene(scene);
                       dialogStage.show();
                   }else{
                       //infoBox("Please enter the correct Password", null, "Failed");
                   }
               }else{
                   //infoBox("Please enter valid Username", null, "Failed");
               }
           }

       }catch (Exception e){
           e.printStackTrace();
       }

    }*/

//    public void signUp(ActionEvent event) {
//        String um = usernamem.getText();
//        String pm = passm.getText();
//
//        // DB db = new DB(connection);
//
//        if (dbConnection.signUp(um, pm)) {
//            infoBox("Successfully signed up", null, "Correct");
//        } else {
//            infoBox("Something went wrong", null, "Failed");
//        }
//        usernamem.clear();
//        passm.clear();
//        try {
//            dbConnection.dbDisconnect();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.out.println("database disconnection");
//        }
//    }


    public void signUp(ActionEvent event) {

        try {
            changeScene(event, "/view/signUpView.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void infoBox(String infoMessage, String headerText, String title) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText("Woopppps");
        //alert.showAndWait();
        alert.show();
    }

    public void homePage(ActionEvent event) throws IOException {
        Stage window = (Stage) backhomebutton.getScene().getWindow();
        window.close();

        /*Parent backHomeScene = FXMLLoader.load(getClass().getResource("/view/StartMenu.fxml"));
        Scene scene = new Scene(backHomeScene);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("Home page");
        window.setScene(scene);
        window.show();
        window.close();*/
    }

    public void buildBorrowList() {

        if (gridPane != null) {

        }
        gridPane = new GridPane();
        gridPane.setHgap(40);
        gridPane.setVgap(20);
        gridPane.add(new Label("  Title"), 0, 0);
        gridPane.add(new Label("  Borrow date"), 1, 0);
        gridPane.add(new Label(" Return date"), 2, 0);

        for (int i = 0; i < BookListController.myBorrowedBooks.size(); i++) {
            gridPane.add(new Label(BookListController.myBorrowedBooks.get(i).getTitle()), 0, i + 1);
            gridPane.add(new Label(BookListController.myBorrowedBooks.get(i).getBorrowDate()), 1, i + 1);
            gridPane.add(new Label(BookListController.myBorrowedBooks.get(i).getReturnDate()), 2, i + 1);

        }
        anchorpin.setContent(gridPane);

    }

    public void forgotPassword(ActionEvent event){

        FXMLLoader loader = new FXMLLoader();
        //loader.setLocation(getClass().getResource("BookList.fxml"));
        Parent bView = null;
        try {
            bView = loader.load(getClass().getResource("/view/forgotPassVIew.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(bView);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dbConnection = new DBConnection();
        List<BorrowBook> borrowSelection = BookListController.myBorrowedBooks;



        //buildBorrowList();

    }
}





