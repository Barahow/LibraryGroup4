package Search;


import javafx.beans.property.SimpleStringProperty;

public class BorrowBook extends Book {

    private SimpleStringProperty borrowDate;
    private SimpleStringProperty returnDate;


    public BorrowBook(String isbn, String title, String author, boolean availability, String borrowDate, String returnDate) {
        super(isbn, title, author, availability);
        this.borrowDate = new SimpleStringProperty(borrowDate);
        this.returnDate = new SimpleStringProperty(returnDate);
    }

    public String getTitle() {
        return title.get();
    }


    public String getBorrowDate() {
        return borrowDate.get();
    }

    public SimpleStringProperty borrowDateProperty() {
        return borrowDate;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate.set(borrowDate);
    }

    public String getReturnDate() {
        return returnDate.get();
    }

    public SimpleStringProperty returnDateProperty() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate.set(returnDate);
    }
}
