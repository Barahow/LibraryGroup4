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
    //SearchCatalog se = new SearchCatalog();
    //CachedRowSetImpl cachedRowSet; retrieve from existing columnDB
    ArrayList<Book> books = new ArrayList<>();
    static int userId;
    static String email;




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
                    if (resultSet.getString(2).contains(bookname) && resultSet.getBoolean(4)) {
                        //  System.out.println(resultSet.getString(1) + ",   " + resultSet.getString(2));
                        Book book = new Book(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getBoolean(4));
                        
                        books.add(book);
                    }
                }
                return books;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
    }


    public ArrayList<Book> fetchAllBooks() {
        String st = "SELECT * FROM book;";
        try {
            resultSet = statement.executeQuery(st);
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
    }

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
        String sqlMem = "SELECT * FROM account;";
        System.out.println("i am here");
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlMem);

            while (resultSet.next()) {
                //System.out.println(resultSet.getString(1) + "  "+resultSet.getString(2));
                if (um.equals(resultSet.getString("email"))) {
                    //System.out.println("Username was correct!");
                    if (pm.equals(resultSet.getString("password"))) {
                        //infoBox("Login Successful", null, "Success");
                        userId = (resultSet.getInt(1));
                        email = (resultSet.getString(7));

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

    public boolean signUp(String SSN, String name, String address, String phoneNumber,
                          String email, String membertype ) {

        try {
            Statement stateSignUp = connDB().createStatement();
            String sqlSign = "INSERT INTO member (SSN,name,address,phone_number,email,membertype) " +
                    "VALUES('"+SSN+"','"+name+"', '"+address+"'," +
                    " '"+phoneNumber+"','"+email+"','"+membertype+"');";
            stateSignUp.executeUpdate(sqlSign);
            return true;
        } catch (SQLException e) {
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
    public void uppdateQuery(int member_id, String book_id,String rd,String id) throws SQLException {

        String borrowSt = "SELECT * FROM borrowedby;";
        Statement statement = connDB().createStatement();
        ResultSet resultSet = statement.executeQuery(borrowSt);

        String st = "INSERT INTO borrowedby (member_memberid, book_isbn, returndate, borroweddate) " +
                "VALUES('"+member_id+"', '"+book_id+"', '"+rd+"','"+id+"');";
        boolean check = false;
        while (resultSet.next()){
            if ((resultSet.getInt(1)==member_id) && (resultSet.getString(2).equalsIgnoreCase(book_id))){
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



    public List<BorrowBook> fetchBorrowedBooks() {
        List<BorrowBook> books = new ArrayList<>();
        String query = "SELECT * FROM book WHERE available = 0;";
        try {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                books.add(new BorrowBook(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getBoolean(4), "44444", "23421"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }
// changes availability into false which means cancel from DB
    public void updateAvilablity(String isbn,boolean avilable){
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
                if (resultSet.getString(1).equalsIgnoreCase(isbn)){
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
            ResultSet resultSet = ste.executeQuery(st);

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
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                //userInfo.add(new User(resultSet.getString(1),resultSet.getString(2)));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }*/

    public static int getUserId() {
        return userId;
    }

    public static void setUserId(int userId) {
        DBConnection.userId = userId;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        DBConnection.email = email;
    }
}
