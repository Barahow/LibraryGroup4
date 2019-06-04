package sample;


import model.*;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class DBConnection {

    private Connection conn = null;
    Statement statement;
    Statement statement1;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet;
    ResultSet resultSet1;
    private String bo;
    ArrayList<Book> books = new ArrayList<>();
    static String userId;
    static String email;
    private boolean admin;

    private static Statement stmt = null;

    //Modify this string with your local database/schema name and username and password.
    private static String DB_URL;

    //Modify this SELECT statement to fit your table name and columns, if you want to get some specific columns.
    private static final String MY_SELECT_STATEMENT = "SELECT * FROM customer order by id asc ;";

    //Singleton pattern.
    private static DBConnection instance = null;

    public DBConnection() {
        // DB_URL = "jdbc:mysql://localhost/library?user=root&password=12345&serverTimezone=UTC&useSSL=false";
        //  connection = connDB();
        Properties properties = new Properties();
        //this will point to the database url in my package
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream("properties/dbInfo.properties")) {
            properties.load(in);
            DB_URL = properties.getProperty("dbUrl");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        setupMemberTable();
        connDB();
        setuptable();

    }


    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }

        return instance;
    }

    private Connection connDB() {

        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return conn;
    }

    //@A: DB connection
    public void dbConnection() throws SQLException {

        conn = connDB();
        try {
            statement = conn.createStatement();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        System.out.println("Connection success!");
    }

    //@A: DB disconnection
    public void dbDisconnect() throws SQLException {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // retrieve data from DB
    public ArrayList<Book> getBooks(String bookName) {
        String st = "SELECT * FROM book WHERE title like '%" + bookName + "%';";
        try {
            Statement stat = conn.createStatement();
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

    public ReservedBook fetchReservedBook(Book book){
        String query =  "SELECT * FROM reservedby WHERE book_isbn =" + book.getIsbn()+ ";";
        try {
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()){
                book = new ReservedBook(book.getIsbn(), book.getTitle(),book.getAuthor(),book.getBookType(),book.isAvailability(),
                        book.isReserved(), rs.getDate("reservation_date").toString(), rs.getDate("available_date").toString());
            }

            return (ReservedBook) book;
        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    // @A: sign in using username and password from DB
    public boolean signIn(String um, String pm) throws Exception {
        int count = 0;
        String sqlMem = "SELECT account.email,account.password,member.* " +
                "FROM account,member " +
                "WHERE member.SSN = account.member_SSN;";
        System.out.println("i am here");
        try {
            System.out.println(conn);
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlMem);

            while (resultSet.next()) {
                //System.out.println(resultSet.getString(1) + "  "+resultSet.getString(2));
                if (um.equals(resultSet.getString("email"))) {
                    //System.out.println("Username was correct!");
                    if (pm.equals(resultSet.getString("password"))) {
                        //infoBox("Login Successful", null, "Success");
                        if (resultSet.getInt("membertype") == 0) {
                            admin = false;
                        } else if (resultSet.getInt("membertype") == 1) {
                            admin = true;
                        }
                        userId = (resultSet.getString("SSN"));
                        email = (resultSet.getString("email"));

                        String name = resultSet.getString("name");
                        String address = resultSet.getString("address");
                        String id = resultSet.getString("SSN");
                        String phoneNr = resultSet.getString("phone_number");
                        String email = resultSet.getString("email");

                        DataManager.getInstance().setLoggedInUser(new User(name,address,id,email,admin));

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

    //@A: sign up member or admin from DB


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

    //@A: update DB, borrowed by table
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

    public String[] passwordQuery(String email) {
        String[] returnVal = new String[2];
        String sqlMem = "SELECT password, email FROM account WHERE email='" + email + "';";
        try {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlMem);

            if (resultSet.next()) {
                String password = resultSet.getString("password");
                String name = resultSet.getString("email");

                returnVal[0] = password;
                returnVal[1] = name;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnVal;
    }


    public void returnBook(BorrowBook book) {
        try {
            String query = "update book a INNER JOIN borrowedby b ON (a.isbn = b.book_isbn1) set a.available = ?, b.returndate = ? where a.isbn = ? AND " +
                    "b.book_isbn1 = ?;";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, 1);
            java.util.Date now = new java.util.Date();
//        String pattern = "yyyy-MM-dd";
//        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
//        String mysqlDateString = formatter.format(now);
            preparedStmt.setDate(2, new Date(now.getTime()));
            preparedStmt.setInt(3, book.getIsbn());
            preparedStmt.setInt(4, book.getIsbn());

            // execute the java preparedstatement
            preparedStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void reserveBook(ReservedBook book) {
        try {
            String query = "INSERT INTO reservedby VALUES (?,?,?,?);";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
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


    private void updateBookAvailability(BorrowBook book) {
        try {
            String query = " UPDATE book SET available = ? WHERE isbn=?;";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, 1);
            preparedStmt.setInt(2, book.getIsbn());
            preparedStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<BorrowBook> fetchBorrowedBooksFromUserID(int userid) {
        ArrayList<BorrowBook> books = new ArrayList<>();
        String query = "SELECT * FROM borrowedby WHERE member_member_id = " + userid + ";";
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Date returnDate = rs.getDate("returndate");
                Date dueDate = rs.getDate("duedate");
                Date issuedate = rs.getDate("issuedate");
                String returnD;
                if (returnDate == null) {
                    returnD = "not returned";
                } else {
                    returnD = returnDate.toString();
                }
                int isb = rs.getInt("book_isbn1");
//                Book book = fetchBook(isb);
//                books.add(new BorrowBook(book.getIsbn(), book.getTitle(), book.getAuthor(), book.isAvailabe(),
//                        issuedate.toString(), returnD, dueDate.toString()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }


    //@A: changes availability into false which means cancel from DB
    public void updateAvilablity(int isbn, boolean avilable) {
        int available = 0;
        if (avilable) {
            available = 1;
        }
        String st_av = "UPDATE book SET available = " + available + " WHERE isbn = " + isbn + ";";

        try {
            Statement ste = conn.createStatement();
            ste.executeUpdate(st_av);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateReservation(int isbn, boolean avilable) {
        int available = 0;
        if (avilable) {
            available = 1;
        }
        String st_av = "UPDATE book SET reserved = " + available + " WHERE isbn = " + isbn + ";";
        try {
            Statement ste = conn.createStatement();
            ste.executeUpdate(st_av);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // this is method is to setup my book table to fetch
    public void setuptable() {
        String Table_Name = "book"; //we could return this...

        if (conn == null)
            conn = connDB();

        if (conn != null) {

            Statement statement = null;
            // ResultSet resultSet = null;

            try {
                statement = conn.createStatement();
                DatabaseMetaData dbm = conn.getMetaData();
                ResultSet Tables = dbm.getTables(null, null, Table_Name, null);

                // Loop through all result rows and print them.
                if (Tables.next()) {
                    System.out.println(" Tables " + Table_Name + " already exist, ready to go ");

                } else {
                    stmt.execute("CREATE TABLE " + Table_Name + "("
                            + "  isbn int(11) primary key, \n"
                            + "   title varchar(45), \n"
                            + "  author varchar(45), \n"
                            + " bookcategory_typeid int(45), \n"
                            + " available boolean default true "
                            + ")");


                }
            } catch (SQLException ex) {


                // handle any errors
                System.out.println("SQLException: " + ex.getMessage() + "setupdatabase");
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            } finally {

            }
        }
        return;
    }

    private void cleanUp(Statement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException sqlEx) {
            } // ignore
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException sqlEx) {
            } // ignore
        }
    }

    //Release the DB connection
    private void close() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException sqlEx) {
            } // ignore
        }


    }

    //this method is for executing queries.
    public ResultSet ExecuteQuery(String query) {
        ResultSet results;
        try {
            stmt = conn.createStatement();
            results = stmt.executeQuery(query);


        } catch (SQLException e) {
            e.printStackTrace();
            return null;

        } finally {

        }

        return results;
    }

    // used for ex creating inserting data to the database or creating a table
    public boolean executeAction(String qu) {
        try {
            stmt = conn.createStatement();
            stmt.execute(qu);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;

        } finally {

        }

    }

    public boolean deleteBook(Book book) throws SQLException {

        try {


            String deleteStatement = "DELETE FROM book WHERE isbn = ?";
            PreparedStatement stmt = conn.prepareStatement(deleteStatement);
            stmt.setString(1, String.valueOf(book.getIsbn()));
            int inform = stmt.executeUpdate();
            // if the table is larger than 0 then return true otherwise return false
            if (inform == 1) {
                return true;
            }
            // if successful retunr true

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        //otherwise return false
        return false;
    }

    public void setupMemberTable() {
        String Table_Name = "member"; //we could return this...

        if (conn == null)
            conn = connDB();

        if (conn != null) {

            Statement statement = null;
            // ResultSet resultSet = null;

            try {
                statement = conn.createStatement();
                DatabaseMetaData dbm = conn.getMetaData();
                ResultSet Tables = dbm.getTables(null, null, Table_Name, null);

                // Loop through all result rows and print them.
                if (Tables.next()) {
                    System.out.println(" Tables " + Table_Name + " already exist, ready to go ");

                } else {
                    stmt.execute("CREATE TABLE " + Table_Name + "("
                            + "  SSN varchar(45) primary key, \n"
                            + "   name varchar(45), \n"
                            + "  address varchar(45), \n"
                            + " phone_number int(45), \n"
                            + "  email  varchar(45), \n"
                            + " membertype boolean default true "
                            + ")");


                }
            } catch (SQLException ex) {


                // handle any errors
                System.out.println("SQLException: " + ex.getMessage() + "setupdatabase");
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            } finally {

            }
        }
        return;
    }

    public boolean deletemember(Member member) throws SQLException {

        try {


            String deleteStatement = "DELETE FROM member WHERE SSN = ?";
            PreparedStatement stmt = conn.prepareStatement(deleteStatement);
            stmt.setString(1, member.getSSN());
            int inform = stmt.executeUpdate();
            // if the table is larger than 0 then return true otherwise return false
            if (inform == 1) {
                return true;
            }
            // if successful retunr true

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    //
//    public ArrayList<Book> getBooks(String bookname) {
//        String st = "SELECT * FROM book;";
//        try {
//            ResultSet resultSet = stmt.getBooks(st);
//            while (resultSet.next()) {
//                //System.out.println("Start");
//                if (resultSet.getString(2).contains(bookname) && resultSet.getBoolean(4)) {
//                    //  System.out.println(resultSet.getString(1) + ",   " + resultSet.getString(2));
//                    Book book = new Book(
//                            resultSet.getString(1),
//                            resultSet.getInt(2),
//                            resultSet.getString(3),
//                            resultSet.getInt(4),
//                            resultSet.getBoolean(5));
//                    book.add(book);
//                }
//                re
//            }
//
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
    public boolean SearchBook(Book book) throws SQLException {

        try {


            String deleteStatement = "SELECT FROM book WHERE title = ?";
            PreparedStatement stmt = conn.prepareStatement(deleteStatement);
            stmt.setString(2, book.getTitle());
            int inform = stmt.executeUpdate();
            // if the table is larger than 0 then return true otherwise return false
            if (inform == 1) {
                return true;
            }
            // if successful retunr true

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }


    private void query(
            String sqlStatement,
            ResultParser resultParser,
            ArrayList collection
    ) {
        if (conn == null)
            conn = connDB();

        if (conn != null) {

            Statement statement = null;
            ResultSet resultSet = null;

            try {
                statement = conn.createStatement();
                resultSet = statement.executeQuery(sqlStatement);
                resultParser.parse(resultSet, collection);
            } catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            } finally {
                closeAll(statement, resultSet);
            }
        }
    }

    private void executeUpdate(String sqlStatement) {
        if (conn == null)
            conn = connDB();

        if (conn != null) {

            Statement statement = null;
            ResultSet resultSet = null;

            try {
                statement = conn.createStatement();
                statement.executeUpdate(sqlStatement);
            } catch (SQLException ex) {
                // handle any errors
                System.out.println("SQLException: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
                System.out.println("VendorError: " + ex.getErrorCode());
            } finally {
                closeAll(statement, resultSet);
            }
        }
    }


    private void closeAll(Statement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException sqlEx) {
                System.out.println(sqlEx);
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException sqlEx) {
                System.out.println(sqlEx);
            }
        }
        //Release the DB connection
        if (conn != null) {
            try {
                conn.close();
                conn = null;
            } catch (SQLException sqlEx) {
                System.out.println(sqlEx);
            }
        }
    }

    public ArrayList getMemberSSN(String SSN) {
        ArrayList collectionResult = new ArrayList();

        String selectStatement = "SELECT * FROM member WHERE SSN = '" + SSN + "';";

        query(selectStatement, (resultSet, collection) -> {
            try {
                // Put all result rows in the collection.
                while (resultSet.next()) {
                    String ssn = resultSet.getString("SSN");
                    String name = resultSet.getString("name");
                    String address = resultSet.getString("address");
                    String phonenumber = resultSet.getString("phone_number");
                    String email = resultSet.getString("email");
                    Boolean membertype = resultSet.getBoolean("membertype");
                    collection.add(new Member(ssn, name, address, phonenumber, email, membertype));
                    break; //As soon as we find ONE result, we're done!
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }, collectionResult);
        return collectionResult;
    }

    public ArrayList getAccounts(String Email, String Password) {
        ArrayList collectionResult = new ArrayList();

        String selectStatement = "SELECT * FROM account WHERE email = '" + Email + "' and password = '" + Password + "'";

        query(selectStatement, (resultSet, collection) -> {
            try {
                // Put all result rows in the collection.
                while (resultSet.next()) {
                    String email = resultSet.getString("email");
                    String password = resultSet.getString("Password");
                    String memberssn = resultSet.getString("member_SSN");
                    collection.add(new Account(email, password, memberssn));
                    break; //As soon as we find ONE result, we're done!

                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }, collectionResult);
        return collectionResult;
    }

    public void updateMember(String SSN, String address, String phoneNumber, String email) {
        String sqlStatement = "UPDATE member SET address, phone_number, email = '" + address + "', '" + phoneNumber + "', '" + email + "' WHERE ssn = " + SSN + ";";
        executeUpdate(sqlStatement);


    }

    public void updateAccount(String Email, String Password, String SSN) {
        String Sqlstatement = "UPDATE account SET email, password = '" + Email + "', '" + Password + "' WHERE member_SSN = " + SSN + ";";
        executeUpdate(Sqlstatement);

    }

    public ArrayList getMemberType(String membertype) {
        ArrayList collectionResult = new ArrayList();

        String selectStatement = "SELECT * FROM member WHERE membertype = " + membertype + "";

        query(selectStatement, (resultSet, collection) -> {
            try {
                // Put all result rows in the collection.
                while (resultSet.next()) {
                    String SSN = resultSet.getString("email");
                    String Name = resultSet.getString("Password");
                    String Address = resultSet.getString("member_SSN");
                    String Phone_Number = resultSet.getString("phone_number");
                    String email = resultSet.getString("email");
                    Boolean MemberType = resultSet.getBoolean("membertype");

                    collection.add(new Member(SSN, Name, Address, Phone_Number, email, MemberType));
                    break; //As soon as we find ONE result, we're done!

                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }, collectionResult);
        return collectionResult;
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

    public boolean getAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
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


            statement = conn.createStatement();
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

    public void updateBookCatagory(String title,int bookTypeId){

        String st_av = "UPDATE book SET bookcategory_typeid =" +
                " " + bookTypeId + " WHERE title = '" + title + "'";
        String st = "SELECT * FROM book;";

        try {
            Statement ste = conn.createStatement();
            ResultSet resultSet = ste.executeQuery(st);

            while (resultSet.next()) {
                if (resultSet.getString(2).equalsIgnoreCase(title)) {
                    Statement stet = connDB().createStatement();
                    stet.executeUpdate(st_av);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ReservedBook> get_Reservation(String SSN) {
        ArrayList<ReservedBook> tempe = new ArrayList<>();
        //int isbn, String title, String author, int bookType, boolean availability,
        // boolean reserved, String reservationDate, String avilabledate

        String sql =  "SELECT * FROM reservedby,book WHERE reservedby.member_SSN = '" + SSN + "';";

        try {


            Statement statement1 = conn.createStatement();
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
                        resultSet1.getDate("reservation_date").toString(),
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

}





