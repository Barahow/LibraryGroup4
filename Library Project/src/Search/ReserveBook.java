package Search;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class ReserveBook {
    IntegerProperty isbn;
    StringProperty title;
    StringProperty SSN;
    StringProperty name;

    public ReserveBook(IntegerProperty isbn, StringProperty title, StringProperty SSN, StringProperty name) {
        this.isbn = isbn;
        this.title = title;
        this.SSN = SSN;
        this.name = name;
    }

    public int getIsbn() {
        return isbn.get();
    }

    public IntegerProperty isbnProperty() {
        return isbn;
    }

    public void setIsbn(int isbn) {
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

    public String getSSN() {
        return SSN.get();
    }

    public StringProperty SSNProperty() {
        return SSN;
    }

    public void setSSN(String SSN) {
        this.SSN.set(SSN);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }
}
