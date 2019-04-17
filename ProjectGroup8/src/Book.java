import java.util.ArrayList;

public class Book {

    private String name;
    private String ISBN;
    private double price;
    private ArrayList<String> authorList = new ArrayList<>();

    public Book(String name, String ISBN, double price, String... authorList){
           this.name = name;
           this.ISBN = ISBN;
           this.price = price;
           this.authorList = new ArrayList<>();
        for (String s: authorList
             ) {
            this.authorList.add(s);
        }
    }

    public String getName() {
        return name;
    }
    public String getISBN(){
        return ISBN;

    }
    public double getPrice(){
        return price;
    }
    public ArrayList<String> getAuthorList(){
        return authorList;
    }


    @Override
    public String toString() {
        return "Name: " + name + '\n' +
                "ISBN: " + ISBN + '\n' +
                "price: " + price + "\n" +
                "authorList=" + authorList.toString() + "\n";
    }
}
