package Search;


import javafx.beans.property.SimpleStringProperty;

import java.sql.Date;

public class BorrowBook extends Book {

    private SimpleStringProperty borrowDate;
    private SimpleStringProperty returnDate;

    private Date actualBorrowDate;
    private Date actualReturnDate;


    public BorrowBook(int isbn, String title,  String author, int bookType, boolean availability,Date borrowDate, Date returnDate) {
        super(isbn, title, author,bookType ,availability);
        actualBorrowDate = borrowDate;
        actualReturnDate = returnDate;
        this.borrowDate = new SimpleStringProperty(borrowDate.toString());
        this.returnDate = new SimpleStringProperty(returnDate.toString());
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

    public Date getActualBorrowDate() {
        return actualBorrowDate;
    }

    public Date getActualReturnDate() {
        return actualReturnDate;
    }
}
