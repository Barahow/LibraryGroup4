package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class BookCategory {
    private final SimpleIntegerProperty typeId;
    private final SimpleStringProperty title;
    private final SimpleStringProperty author;
    private final SimpleStringProperty bookType;





    public String getBookType() {
        return bookType.get();
    }

    public SimpleStringProperty bookTypeProperty() {
        return bookType;
    }

    public BookCategory(int TypeId,String Title, String Author, String BookType) {
        this.title = new SimpleStringProperty(Title);
        this.author = new SimpleStringProperty(Author);
        this.bookType = new SimpleStringProperty(BookType);
        this.typeId = new SimpleIntegerProperty(TypeId);

    }


    public int getTypeId() {
        return typeId.get();
    }

    public SimpleIntegerProperty typeIdProperty() {
        return typeId;
    }

    public String getTitle() {
        return title.get();
    }


public String getAuthor() {
        return author.get();
    }




    public void add(BookCategory book)
    {
    }
}

