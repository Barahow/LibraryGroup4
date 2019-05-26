package model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.CheckBox;

public class Book {


     SimpleIntegerProperty isbn;
     SimpleStringProperty title;
     SimpleStringProperty Author;
     SimpleIntegerProperty bookType;
     SimpleBooleanProperty availability;

    CheckBox checkBox;



    public Book(int isbn,String title,String author, int bookType, boolean availability){

        this.title = new SimpleStringProperty(title);
        this.isbn = new SimpleIntegerProperty(isbn);
        this.Author = new SimpleStringProperty(author);
        this.bookType = new SimpleIntegerProperty(bookType);
        this.availability = new SimpleBooleanProperty(availability);
        this.checkBox = new CheckBox();

    }

    public int getIsbn() {
        return isbn.get();
    }

    public SimpleIntegerProperty isbnProperty() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn.set(isbn);
    }

    public String getTitle() {
        return title.get();
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getAuthor() {
        return Author.get();
    }

    public SimpleStringProperty authorProperty() {
        return Author;
    }

    public void setAuthor(String author) {
        this.Author.set(author);
    }

    public int getBookType() {
        return bookType.get();
    }

    public SimpleIntegerProperty bookTypeProperty() {
        return bookType;
    }

    public void setBookType(int bookType) {
        this.bookType.set(bookType);
    }

    public boolean isAvailability() {
        return availability.get();
    }

    public SimpleBooleanProperty availabilityProperty() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability.set(availability);
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }
}

