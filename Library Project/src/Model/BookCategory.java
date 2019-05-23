package Model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class BookCategory {
    private final SimpleIntegerProperty TypeId;
    private final SimpleStringProperty Title;
    private final SimpleStringProperty Author;
    private final SimpleStringProperty BookType;





    public String getBookType() {
        return BookType.get();
    }

    public SimpleStringProperty bookTypeProperty() {
        return BookType;
    }

    public BookCategory(int TypeId,String Title, String Author, String BookType) {
        this.Title = new SimpleStringProperty(Title);
        this.Author = new SimpleStringProperty(Author);
        this.BookType = new SimpleStringProperty(BookType);
        this.TypeId = new SimpleIntegerProperty(TypeId);

    }


    public int getTypeId() {
        return TypeId.get();
    }

    public SimpleIntegerProperty typeIdProperty() {
        return TypeId;
    }

    public String getTitle() {
        return Title.get();
    }


public String getAuthor() {
        return Author.get();
    }




    public void add(BookCategory book)
    {
    }
}

