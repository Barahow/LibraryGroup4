package model;

import java.util.ArrayList;

public class User {
        String name;
        String address;
        String SSN;
        String email;
        boolean member = true;
        private ArrayList<BorrowBook> borrowedBooks;

        public User(String name, String address, String SSN,
                    String email, boolean member) {
            this.name = name;
            this.address = address;
            this.SSN = SSN;
            this.email = email;
            this.member = member;
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

        public String getSSN() {
            return SSN;
        }

        public void setSSN(String SSN) {
            this.SSN = SSN;
        }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isMember() {
            return member;
        }

        public void setMember(boolean member) {
            this.member = member;
        }

        public ArrayList<BorrowBook> getBorrowedBooks() {
            return borrowedBooks;
        }

        public void setBorrowedBooks(ArrayList<BorrowBook> borrowedBooks) {
            this.borrowedBooks = borrowedBooks;
        }
    }
