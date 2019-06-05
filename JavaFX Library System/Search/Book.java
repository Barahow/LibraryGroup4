package Search;

import javafx.beans.property.*;
import javafx.scene.control.CheckBox;

public class Book {
    private SimpleIntegerProperty isbn;
    private SimpleStringProperty title;
    private SimpleStringProperty Author;
    private SimpleIntegerProperty bookType;
    private SimpleBooleanProperty availability;
    private SimpleBooleanProperty reserved;

    private CheckBox checkBox;


    public Book(int isbn, String title, String author, int bookType, boolean availability, boolean reserved) {

        this.title = new SimpleStringProperty(title);
        this.isbn = new SimpleIntegerProperty(isbn);
        this.Author = new SimpleStringProperty(author);
        this.bookType = new SimpleIntegerProperty(bookType);
        this.availability = new SimpleBooleanProperty(availability);
        this.reserved = new SimpleBooleanProperty(reserved);
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

    public boolean isReserved() {
        return reserved.get();
    }

    public SimpleBooleanProperty reservedProperty() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved.set(reserved);
    }
}
