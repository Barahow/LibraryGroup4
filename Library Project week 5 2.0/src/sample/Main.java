package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {



    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Library System");
        loadLoginView();
    }

    public static void main(String[] args) {
        launch(args);
    }



    private static void loadView(String nameOfView){
        try {
            Parent root = FXMLLoader.load(Main.class.getResource(nameOfView+".fxml"));
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        }
        catch (Exception ex){
            ex.printStackTrace(); //Most likely something wrong with loading files.
        }
    }

    public static void loadLoginView(){
        loadView("../View/LogInView");
    }

    public static void loadLibraryView(){
        loadView("../View/MainMenu");
    }

    public static void LoadRegisterView() {
    loadView("../View/RegisterAccount");

    }

    //We can extend this application later by loading more scene names here...

}

