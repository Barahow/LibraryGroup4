package sample;

import Model.Account;
import Model.Member;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main extends Application {

    DbUtil dbhandler;

    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Library System");
        LoadMainView();
        //loadLoginView();
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

    public static void LoadMainView() {
      loadView("/View/MainPage");

    }

    public static void loadLoginView() {
        loadView("../View/LogInView");
    }

    public static void loadLibraryView() {


        DbUtil dbhandler = new DbUtil();

        Boolean Membertype = true;
        String qu = "SELECT membertype FROM  member where membertype = '"+ Membertype+"'";

        System.out.println(qu);

        ResultSet result = dbhandler.ExecuteQuery(qu);

        try {
            while (result.next()) {
                //result.getInt("membertype");
                      //if member type true
                if (result.getBoolean("membertype")){
                    loadView("../View/MenuMember");

                    // if member type false
                }else if (!result.getBoolean("membertype"))
                    loadView("../View/MainMenu");
                }




             //  loadView(String.valueOf(result.getBoolean("membertype")? "../View/MenuMember" : "../View/MainMenu"));

            } catch (SQLException e1) {
            e1.printStackTrace();
        }
        }

        // for librarian and membertype false
        //loadView("../view/MainMenu");


        // for memb

       // loadView("../view/MenuMember");










    public static void LoadRegisterView() {
        loadView("../View/RegisterAccount");

    }
}

    //We can extend this application later by loading more scene names here...


