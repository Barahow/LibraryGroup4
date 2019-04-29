package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.model.Book;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class MainViewController implements Initializable {
    private final String FILE_NAME = "books.ser";
    @FXML
    private Button eturnBookButton, borrowBookButton, removeBookButton, searchBookButton, reservationButton;

    @FXML
    private TextField amountTextField;
    @FXML
    private TextField bookTextField;

    private ArrayList<Book> books = new ArrayList<>();

    @FXML
    private void pressButton(ActionEvent event) {
        String value = ((Button) event.getSource()).getText();



        switch (value){
            case "Returned books":
                changeScene(event, "../views/returnBookView.fxml");
                break;
            case "Borrowed books":
                changeScene(event, "../views/borrowBookView.fxml");
                break;
            case "Search books":
                changeScene(event, "../views/searchBookView.fxml");
                break;
            case "Reservations":
                changeScene(event, "../views/reservationView.fxml");
                break;
            case "Members":

                break;
        }



    }

    private void addBooks() {
        try {
            String name = bookTextField.getText();
            double amount = Integer.valueOf(amountTextField.getText());

            if (amount > 0) {
                books.add(new Book(name, amount));
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Amount can not be negative or 0", ButtonType.OK);
                alert.showAndWait();
            }

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Incorrect Input,", ButtonType.OK);
            alert.showAndWait();
        }

    }

    private void writeToFile() {
        File file = new File(FILE_NAME);
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file))) {
            objectOutputStream.writeObject(books);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void goToViewScene(ActionEvent event) {
        try {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("viewScene.fxml"));
            Parent root = loader.load();

            //((viewSceneController) loader.getController()).setBooks(books);

            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void changeScene(ActionEvent event, String resource) {
        try {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));

            Parent root = loader.load();
         //   ((MainViewController) loader.getController()).setItems(items);
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



