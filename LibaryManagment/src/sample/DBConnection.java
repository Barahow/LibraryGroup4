package Search;


import javafx.event.Event;
import javafx.event.EventHandler;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class DBConnection {
    Connection connection = null;
    public static Connection connDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" +
                    "library?user=root&password=root&useSSL=false\"; //serverTi");
            return conn;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

        return null;
    }
   Statement statement;
    Statement statement1;
    PreparedStatement preparedStatement =null;
    ResultSet resultSet;
    ResultSet resultSet1;
    private String bo;


    ArrayList<Book> books = new ArrayList<>();
    //SearchCatalog se = new SearchCatalog();
    //CachedRowSetImpl cachedRowSet; retrieve from existing columnDB




    public void dbConnection() throws SQLException {


        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","root");
        try {
            statement = connection.createStatement();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        System.out.println("Connection success!");
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
                    Book book = new Book(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4));
                    books.add(book);
                }
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

    public String getSt() {
        return bo;
    }

    public   int checkLogin(String username,String password){
        //Connection con = DButil.con;

        String strr = "SELECT * FROM book;";
        try {
            preparedStatement = connection.prepareStatement(strr);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);
            resultSet = preparedStatement.executeQuery();
            resultSet = statement.executeQuery(strr);
            resultSet1= statement1.executeQuery(strr);
            System.out.println("Before while");
            if(!resultSet.next()) {
                if (resultSet.getString(1).equalsIgnoreCase(username)
                        && resultSet.getString(2).equalsIgnoreCase(password)) {
                    return 1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public int signUp(String username, String password) throws SQLException {
        /*String sqlSignUp = "INSERT INTO member (username,password) VALUES(" +
                "'"+ username +"','"+password+"');";*/
        String sqlSign = "INSERT INTO login (username,passw)" +
                " VALUES('"+username+"','"+password+"');";
        Statement stateSignUp = connection.createStatement();
        stateSignUp.executeUpdate(sqlSign);
    return 0;
    }

}
