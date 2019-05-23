package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.text.SimpleDateFormat;
import java.util.Date;


public class BookIssue {

    private SimpleStringProperty Member_SSN;
    private SimpleStringProperty Book_ISBN;
    private SimpleStringProperty Due_Date;
    private SimpleStringProperty Return_Date;
    private SimpleStringProperty Issue_Date;

    public BookIssue(String member_SSN, String book_ISBN, String due_Date, String return_Date, String issue_Date) {
        this.Member_SSN = new SimpleStringProperty(member_SSN);
        this.Book_ISBN = new SimpleStringProperty(book_ISBN);
        this.Due_Date = new SimpleStringProperty(due_Date);
        this.Return_Date = new SimpleStringProperty(return_Date);
        this.Issue_Date =  new SimpleStringProperty(issue_Date);
    }

    public String getMember_SSN() {
        return Member_SSN.get();
    }

    public SimpleStringProperty member_SSNProperty() {
        return Member_SSN;
    }

    public String getBook_ISBN() {
        return Book_ISBN.get();
    }

    public SimpleStringProperty book_ISBNProperty() {
        return Book_ISBN;
    }

    public String getDue_Date() {
        return Due_Date.get();
    }

    public SimpleStringProperty due_DateProperty() {
        return Due_Date;
    }

    public String getReturn_Date() {
        return Return_Date.get();
    }

    public SimpleStringProperty return_DateProperty() {
        return Return_Date;
    }

    public String getIssue_Date() {
        return Issue_Date.get();
    }

    public SimpleStringProperty issue_DateProperty() {
        return Issue_Date;
    }
}
