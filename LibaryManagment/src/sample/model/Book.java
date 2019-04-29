package sample.model;

import java.util.ArrayList;

public class Book {
    private String name;
    private String ISBN;
    private double price;
    private ArrayList<String> authorList = new ArrayList<>();

    public Book(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public Book(String name, String ISBN, double price, ArrayList<String> authorList){
        this.name = name;
        this.ISBN = ISBN;
        this.price = price;
        this.authorList = authorList;
    }
    public String getName(){
        return name;
    }
    public String getISBN(){
        return ISBN;
    }
    public double getPrice(){
        return price;
    }
    public ArrayList<String> getAuthorList() {
        return authorList;
    }
}
