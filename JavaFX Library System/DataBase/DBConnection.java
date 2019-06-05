package DataBase;


import Home.DataManager;
import Search.Book;
import Search.BorrowBook;
import Search.View.ReservedBook;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class DBConnection {
    Connection connection = null;
    Statement statement;
    Statement statement1;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet;
    ResultSet resultSet1;
    private String bo;
    ArrayList<Book> books = new ArrayList<>();
    static String userId;
    static String email;


    public static Connection connDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("" +
                    "jdbc:mysql://localhost:3306/" +
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
    public ArrayList<Book> getBooks(String bookName) {
        String st = "SELECT * FROM book WHERE title like '%" + bookName + "%';";
        try {
            Statement stat = connection.createStatement();
            ResultSet resultSet = stat.executeQuery(st);
            while (resultSet.next()) {
                //System.out.println("Start");
                if (resultSet.getString("title").contains(bookName)) {
                    System.out.println(st);
                    //  System.out.println(resultSet.getString(1) + ",   " + resultSet.getString(2));
                    boolean available = false;
                    boolean reserved = false;
                    if (resultSet.getInt("available") == 1) {
                        available = true;
                    }
                    if (resultSet.getInt("reserved") == 1) {
                        reserved = true;
                    }
                    Book book =   new Book(
                            resultSet.getInt("isbn"),
                            resultSet.getString("title"),
                            resultSet.getString("author"),
                            resultSet.getInt("bookcategory_typeid"),
                            available,reserved);

                    if(!available){
                        book = fetchBorrowedBook(book);
                    }

                    if(reserved){
                        book = fetchReservedBook(book);
                    }

                    books.add(book);

                }
            }
            return books;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public ReservedBook fetchReservedBook(Book book){
        String query =  "SELECT * FROM reservedby WHERE book_isbn =" + book.getIsbn()+ ";";
        try {
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()){
                book = new ReservedBook(book.getIsbn(), book.getTitle(),book.getAuthor(),book.getBookType(),book.isAvailability(),
                        book.isReserved(), rs.getDate("resrvation_date").toString(), rs.getDate("available_date").toString());
            }

            return (ReservedBook) book;
        }catch (SQLException e){
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
                        userId = String.valueOf((resultSet.getInt(1)));
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
                          String email, String membertype) {

        try {
            Statement stateSignUp = connDB().createStatement();
            String sqlSign = "INSERT INTO member (SSN,name,address,phone_number,email,membertype) " +
                    "VALUES('" + SSN + "','" + name + "', '" + address + "'," +
                    " '" + phoneNumber + "','" + email + "','" + membertype + "');";
            stateSignUp.executeUpdate(sqlSign);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Update borrowed books.
     *
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
                "VALUES((SELECT ssn FROM member WHERE ssn = '" + member_SSN + "')," +
                "(SELECT isbn FROM book WHERE isbn = '" + book_isbn + "')," +
                "'" + rd + "','" + rd + "','" + id + "');";
        boolean check = false;
        while (resultSet.next()) {
            if ((resultSet.getString(1) == member_SSN) && (resultSet.getString(2).equalsIgnoreCase(String.valueOf(book_isbn)))) {
                System.out.println("Already borrowed");
            } else {
                check = true;
            }
        }
        if (resultSet.getFetchSize() < 1) {
            check = true;
        }
        if (check) {
            Statement state = connDB().createStatement();
            state.executeUpdate(st);
        }
    }


    /* public List<BorrowBook> fetchBorrowedBooks() {
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
    public void updateAvilablity(int isbn, boolean avilable) {
        int available = 0;
        if (avilable) {
            available = 1;
        }
        String st_av = "UPDATE book SET available = " + available + " WHERE isbn = '" + isbn + "'";
        String st = "SELECT * FROM book;";

        try {
            Statement ste = connection.createStatement();
            ResultSet resultSet = ste.executeQuery(st);

            while (resultSet.next()) {
                if (resultSet.getString(1).equalsIgnoreCase(String.valueOf(isbn))) {
                    Statement stet = connDB().createStatement();
                    stet.executeUpdate(st_av);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public ArrayList<BorrowBook> get_Borrowed_History(String SSN) {
        ArrayList<BorrowBook> tempe = new ArrayList<>();

        String sql =
                "SELECT book.isbn, book.title, book.author, book.bookcategory_typeid, " +
                        "book.available,book.reserved," +
                        " borrowedby.issue_date, borrowedby.due_date,borrowedby.return_date " +
                        "FROM book, borrowedby " +
                        "WHERE borrowedby.member_SSN = '" + SSN + "'" +
                        "AND book.isbn = borrowedby.book_isbn;";


        try {


            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {

                System.out.println("Found result!");
                boolean available = false;
                boolean reserved = false;

                if (resultSet.getInt("book.available") == 1) {
                    available = true;
                }

                if (resultSet.getInt("book.reserved") == 1) {
                    reserved = true;
                }

                tempe.add(new BorrowBook(
                        resultSet.getInt("isbn"),
                        resultSet.getString("title"),
                        resultSet.getString("author"),
                        resultSet.getInt("bookcategory_typeid"),
                        available, reserved,
                        resultSet.getDate("issue_date").toString(),
                        resultSet.getDate("due_date").toString(),
                        resultSet.getDate("return_date").toString()));

            }

            System.out.println("*********** ########### ***************");
            for (int i = 0; i < tempe.size(); i++) {
                System.out.println(tempe.get(i).getDueDate());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tempe;
    }

    public ArrayList<ReservedBook> get_Reservation(String SSN) {
        ArrayList<ReservedBook> tempe = new ArrayList<>();
        //int isbn, String title, String author, int bookType, boolean availability,
        // boolean reserved, String reservationDate, String avilabledate

        String sql =  "SELECT * FROM reservedby,book WHERE reservedby.member_SSN = '" + SSN + "';";

        try {


            Statement statement1 = connection.createStatement();
            ResultSet resultSet1 = statement1.executeQuery(sql);

            System.out.println("**** before while result!");
            while (resultSet1.next()) {

                System.out.println("Found result!");
                boolean available = false;
                boolean reserved = false;

                if (resultSet1.getInt("book.available") == 1) {
                    available = true;
                }

                if (resultSet1.getInt("book.reserved") == 1) {
                    reserved = true;
                }



                tempe.add(new ReservedBook(
                        resultSet1.getInt("isbn"),
                        resultSet1.getString("title"),
                        resultSet1.getString("author"),
                        resultSet1.getInt("bookcategory_typeid"),
                        available, reserved,
                        resultSet1.getDate("resrvation_date").toString(),
                        resultSet1.getDate("available_date").toString()
                ));
                System.out.println(tempe);
            }

            System.out.println("*********** ########### ***************");
            for (int i=0;i<tempe.size();i++){
                System.out.println(" *** "+tempe.get(i).getTitle());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tempe;
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
   public void updateReservation(int isbn, boolean avilable) {
       int available = 0;
       if (avilable) {
           available = 1;
       }
       String st_av = "UPDATE book SET reserved = " + available + " WHERE isbn = " + isbn + ";";
       try {
           Statement ste = connection.createStatement();
           ste.executeUpdate(st_av);
       } catch (SQLException e) {
           e.printStackTrace();
       }
   }
       public void reserveBook(ReservedBook book) {
           try {
               String query = "INSERT INTO reservedby VALUES (?,?,?,?);";
               PreparedStatement preparedStmt = connection.prepareStatement(query);
               preparedStmt.setString(1, DataManager.getInstance().getLoggedInUser().getSSN());
               preparedStmt.setInt(2, book.getIsbn());
               preparedStmt.setDate(3, Date.valueOf(book.getReservationDate()));
               preparedStmt.setDate(4, Date.valueOf(book.getAvilabledate()));

               // execute the java preparedstatement
               preparedStmt.executeUpdate();
           } catch (SQLException e) {
               e.printStackTrace();
           }
       }
    public BorrowBook fetchBorrowedBook(Book book){
        String query =  "SELECT * FROM borrowedby WHERE book_isbn =" + book.getIsbn()+ ";";
        try {
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()){
                book = new BorrowBook(book.getIsbn(), book.getTitle(),book.getAuthor(),book.getBookType(),book.isAvailability(),
                        book.isReserved(), rs.getDate("issue_date").toString(), rs.getDate("return_date").toString(),
                        rs.getDate("due_date").toString());
            }

            return (BorrowBook) book;
        }catch (SQLException e){
            e.printStackTrace();
        }

        return (BorrowBook) book;
    }




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