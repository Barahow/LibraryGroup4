package Search;

public class Book {
    String isbn;
    String title;
    String author;
    String availability;

    public Book(String isbn, String title, String author, String availability) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;

        this.setAvailability(availability);
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    public String isAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }
}
