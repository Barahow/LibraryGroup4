package Search;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    //DBConnection connection = new DBConnection();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Search.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("LMS.css").toExternalForm());
        primaryStage.setTitle("Library");
        primaryStage.setScene(scene);
        primaryStage.show();
        //connection.dbConnection();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
