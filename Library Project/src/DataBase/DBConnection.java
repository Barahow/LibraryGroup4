package DataBase;


import Login.User;
import Search.Book;
import Search.BorrowBook;
import javafx.event.Event;
import javafx.event.EventHandler;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBConnection {
    Connection connection = null;
    Statement statement;
    Statement statement1;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet;
    ResultSet resultSet1;
    private String bo;
    //SearchCatalogController se = new SearchCatalogController();
    //CachedRowSetImpl cachedRowSet; retrieve from existing columnDB
    ArrayList<Book> books = new ArrayList<>();
    private static String userId;
    private static String email;
    private boolean admin;




    public static Connection connDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" +
                    "library?user=root&password=root&useSSL=false");
            return conn;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

        return null;
    }

    public void dbConnection() throws SQLException {

        connection = connDB();
        try {
            statement = connection.createStatement();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        System.out.println("Connection success!");
    }

    public DBConnection() {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/library", "root", "root");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // retrieve data from DB
    public ArrayList<Book> executeQuery(String bookname) {
        String st = "SELECT * FROM book;";
        try {
            resultSet = statement.executeQuery(st);
            while (resultSet.next()) {
                //System.out.println("Start");
                if (resultSet.getString(2).contains(bookname)) {
                    //  System.out.println(resultSet.getString(1) + ",   " + resultSet.getString(2));
                    boolean available = false;
                    if(resultSet.getInt(5)==1){
                        available = true;
                        Book book = new Book(
                                resultSet.getInt(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getInt(4),
                                available);
                        books.add(book);
                    }

                }
            }
            return books;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


  /*  public ArrayList<Book> fetchAllBooks() {
        String st = "SELECT * FROM book;";
        try {
            resultSet = statement.getBooks(st);
            while (resultSet.next()) {
                //  System.out.println(resultSet.getString(1) + ",   " + resultSet.getString(2));
                Book book = new Book(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getBoolean(4));
                books.add(book);
            }
            return books;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }*/

    public void dbDisconnect() throws SQLException {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean signIn(String um, String pm) throws Exception {
        int count = 0;
        String sqlMem = "SELECT account.email,account.password,member.membertype,member.SSN " +
                "FROM account,member " +
                "WHERE member.SSN = account.member_SSN;";
        System.out.println("i am here");
        try {
            System.out.println(connection);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlMem);

            while (resultSet.next()) {
                //System.out.println(resultSet.getString(1) + "  "+resultSet.getString(2));
                if (um.equals(resultSet.getString("email"))) {
                    //System.out.println("Username was correct!");
                    if (pm.equals(resultSet.getString("password"))) {
                        //infoBox("Login Successful", null, "Success");
                        if(resultSet.getInt("membertype")==0){
                            admin = false;
                        }else if(resultSet.getInt("membertype") == 1){
                            admin = true;
                        }
                        userId = (resultSet.getString("SSN"));
                        email = (resultSet.getString("email"));
                        return true;
                    } else {
                        //pass not correct
                        //infoBox("pass not correct", null, "Failed");
                        return false;
                    }
                } else {
                    //infoBox("username not correct", null, "Failed");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean signUp(String ssn, String name, String address, String phone, String email, String password){

        try {
            Statement stateSignUp = connDB().createStatement();
            String sqlSign = "INSERT INTO member " +
                    "VALUES('"+ssn+"','"+name+"','"+address+"','"+phone+"','"+email+"',0);";
            String sqlSign2 = "INSERT INTO account " +
                    "VALUES('" + email + "','" + password + "',(SELECT ssn FROM member WHERE ssn = '" + ssn + "'));";
            stateSignUp.executeUpdate(sqlSign);
            stateSignUp.executeUpdate(sqlSign2);
            return true;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Update borrowed books.
     * @param
     * @return
     * @throws SQLException
     */
// update DB (borredby table)
    public void uppdateQuery(String member_SSN, int book_isbn, Date rd, Date id) throws SQLException {

        String borrowSt = "SELECT * FROM borrowedby;";
        Statement statement = connDB().createStatement();
        ResultSet resultSet = statement.executeQuery(borrowSt);

        String st = "INSERT INTO borrowedby " +
                "VALUES((SELECT ssn FROM member WHERE ssn = '"+member_SSN+"')," +
                "(SELECT isbn FROM book WHERE isbn = '"+book_isbn+"')," +
                "'" + rd + "','" + rd + "','" + id + "');";
        boolean check = false;
        while (resultSet.next()){
            if ((resultSet.getString(1)==member_SSN) && (resultSet.getString(2).equalsIgnoreCase(String.valueOf(book_isbn)))){
                System.out.println("Already borrowed");
            }else {
                check = true;
            }
        }
        if(resultSet.getFetchSize()<1){
            check = true;
        }
        if (check){
            Statement state = connDB().createStatement();
            state.executeUpdate(st);
        }
    }



    /*public List<BorrowBook> fetchBorrowedBooks() {
        List<BorrowBook> books = new ArrayList<>();
        String query = "SELECT * FROM book WHERE available = 0;";
        try {
            ResultSet rs = statement.getBooks(query);
            while (rs.next()) {
                books.add(new BorrowBook(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getBoolean(4), "44444", "23421"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }*/
// changes availability into false which means cancel from DB
public void updateAvilablity(int isbn, boolean avilable){
    int available = 0;
    if(avilable){
        available = 1;
    }
    String st_av = "UPDATE book SET available = " + available + " WHERE isbn = '" + isbn + "'";
    String st = "SELECT * FROM book;";

    try {
        Statement ste = connection.createStatement();
        ResultSet resultSet = ste.executeQuery(st);

        while (resultSet.next()) {
            if (resultSet.getString(1).equalsIgnoreCase(String.valueOf(isbn))){
                Statement stet = connDB().createStatement();
                stet.executeUpdate(st_av);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
  /*  public void updateUnavailability(String isbn,boolean avilable){
        int available = 1;
        if(avilable){
            available = 0;
        }
        String st_av = "UPDATE book SET available = " + available + " WHERE isbn = '" + isbn + "'";
        String st = "SELECT * FROM book;";

        try {
            Statement ste = connection.createStatement();
            ResultSet resultSet = ste.getBooks(st);

            while (resultSet.next()) {
                if (resultSet.getString(1).equalsIgnoreCase(isbn)){
                    Statement stet = connDB().createStatement();
                    stet.executeUpdate(st_av);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/

   /* public List<User> displayUserInfo(String name, String email){
        List<User> userInfo = new ArrayList<>();
        String query = "SELECT name FROM user ;";
        try {
            ResultSet resultSet = statement.getBooks(query);
            while (resultSet.next()){
                //userInfo.add(new User(resultSet.getString(1),resultSet.getString(2)));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }*/

    public static String getUserId() {
        return userId;
    }

    public static void setUserId(String userId) {
        DBConnection.userId = userId;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        DBConnection.email = email;
    }
}
