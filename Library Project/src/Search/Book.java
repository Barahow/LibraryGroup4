package Search;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.CheckBox;

public class Book {
    StringProperty isbn;
    StringProperty title;
    StringProperty author;
    BooleanProperty availability;
    CheckBox checkBox;

    public Book(String isbn, String title, String author, boolean availability) {
        this.isbn = new SimpleStringProperty(isbn);
        this.title = new SimpleStringProperty(title);
        this.author = new SimpleStringProperty(author);
        this.availability = new SimpleBooleanProperty(availability);
        this.checkBox = new CheckBox();
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public String getIsbn() {
        return isbn.get();
    }

    public StringProperty isbnProperty() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn.set(isbn);
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getAuthor() {
        return author.get();
    }

    public StringProperty authorProperty() {
        return author;
    }

    public void setAuthor(String author) {
        this.author.set(author);
    }

    public boolean isAvailability() {
        return availability.get();
    }

    public BooleanProperty availabilityProperty() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability.set(availability);
    }
}
