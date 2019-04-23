package sample.ListofBooks;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Book {

    private final SimpleStringProperty  Title;
    private final SimpleIntegerProperty ID;
    private final  SimpleStringProperty Author;
    private final   SimpleStringProperty ISBN;
    private final SimpleBooleanProperty availiability;

  public Book(String Title, int ID, String Author, String ISBN, boolean availiability  ) {

      this.Title = new SimpleStringProperty(Title);
      this.ID = new SimpleIntegerProperty(ID);
      this.Author = new SimpleStringProperty(Author);
      this.ISBN = new SimpleStringProperty(ISBN);
      this.availiability = new SimpleBooleanProperty(availiability);

  }

    public String getTitle() {
        return Title.get();
    }


    public int getID() {
        return ID.get();
    }



    public String getAuthor() {
        return Author.get();
    }


    public String getISBN() {
        return ISBN.get();
    }



    public boolean isAvailiability() {
        return availiability.get();
    }


    }

