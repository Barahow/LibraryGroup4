package Login;

import Search.BorrowBook;

import java.util.ArrayList;

public class User {
    String SSN;
    String name;
    String address;
    String phoneNumber;
    String email;
    boolean membertype = true;
    private ArrayList<BorrowBook> borrowedBooks;

    public User(String SSN, String name, String address,
                String phoneNumber, String email, boolean membertype,ArrayList<BorrowBook> borrowedBooks) {
        this.SSN = SSN;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.membertype = membertype;
        this.borrowedBooks = borrowedBooks;
    }

    public String getSSN() {
        return SSN;
    }

    public void setSSN(String SSN) {
        this.SSN = SSN;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isMembertype() {
        return membertype;
    }

    public void setMembertype(boolean membertype) {
        this.membertype = membertype;
    }
    public ArrayList<BorrowBook> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(ArrayList<BorrowBook> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }
}

