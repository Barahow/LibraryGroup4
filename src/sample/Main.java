package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Main extends Application {

    DBConnection dbhandler;

    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Library System");
        loadView("../view/StartMenu");
    }

    public static void main(String[] args) {
        launch(args);
    }


    private static void loadView(String nameOfView) {
        try {
            Parent root = FXMLLoader.load(Main.class.getResource(nameOfView + ".fxml"));


            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception ex) {
            ex.printStackTrace(); //Most likely something wrong with loading files.
        }
    }

    public static void loadLoginView() {
        loadView("../view/LogInView");
    }

    public static void loadLibraryView() {


        DBConnection dbhandler = new DBConnection();

        Boolean Membertype = true;
        String qu = "SELECT membertype FROM  member where membertype = " + Membertype + "";

        System.out.println(qu);
        assert dbhandler != null;
        ResultSet result = dbhandler.ExecuteQuery(qu);

        try {
            while (result.next()) {
                //result.getInt("membertype");

                if (result.getBoolean("membertype")== true){
                    loadView("../view/MainMenu");

                    //loadView("../view/MenuMember");
                }else if (result.getBoolean("membertype")== false)
                    loadView("../view/MainMenu");
                }




             //  loadView(String.valueOf(result.getBoolean("membertype")? "../view/MenuMember" : "../view/MainMenu"));

            } catch (SQLException e1) {
            e1.printStackTrace();
        }
        }

        // for librarian and membertype false
        //loadView("../view/MainMenu");


        // for memb

       // loadView("../view/MenuMember");










    public static void LoadRegisterView() {
        loadView("../view/RegisterAccount");

    }
}

    //We can extend this application later by loading more scene names here...


