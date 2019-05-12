package Model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Book {

    private final SimpleStringProperty Title;
    private final SimpleIntegerProperty ISBN;
    private final SimpleStringProperty Author;
    private final SimpleIntegerProperty BookType;
    private final SimpleBooleanProperty availiability;




    public int getBookType() {
        return BookType.get();
    }

    public SimpleIntegerProperty bookTypeProperty() {
        return BookType;
    }

    public Book(String Title, int ISBN, String Author, int BookType, boolean availiability) {

        this.Title = new SimpleStringProperty(Title);
        this.ISBN = new SimpleIntegerProperty(ISBN);
        this.Author = new SimpleStringProperty(Author);
        this.BookType = new SimpleIntegerProperty(BookType);
        this.availiability = new SimpleBooleanProperty(availiability);

    }

    public int getISBN() {
        return ISBN.get();
    }

    public SimpleIntegerProperty ISBNProperty() {
        return ISBN;
    }

    public String getTitle() {
        return Title.get();
    }


public String getAuthor() {
        return Author.get();
    }

    public boolean isAvailiability() {
        return availiability.get();

    }


    public void add(Book book)
    {
    }
}

