package model;


import javafx.beans.property.SimpleStringProperty;

import java.sql.Date;

public class BorrowBook extends Book {
        private SimpleStringProperty borrowDate;
        private SimpleStringProperty returnDate;
        private SimpleStringProperty dueDate;

        private String actualBorrowDate;
        private String actualReturnDate;


        public BorrowBook(int isbn, String title,  String author, int bookType,
                          boolean availability, boolean reserved,String borrowDate, String returnDate,String dueDate) {
            super(isbn, title, author,bookType ,availability, reserved);
            actualBorrowDate = borrowDate;
            actualReturnDate = returnDate;
            this.borrowDate = new SimpleStringProperty(borrowDate);
            this.returnDate = new SimpleStringProperty(returnDate);
            this.dueDate = new SimpleStringProperty(dueDate);
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
            return Date.valueOf(actualBorrowDate);
        }

        public Date getActualReturnDate() {
            return Date.valueOf(actualReturnDate);
        }

        public String getDueDate() {
            return dueDate.get();
        }

        public SimpleStringProperty dueDateProperty() {
            return dueDate;
        }

        public void setDueDate(String dueDate) {
            this.dueDate.set(dueDate);
        }
    }
