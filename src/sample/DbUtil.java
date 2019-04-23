package sample;


import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DbUtil {

    private Connection conn = null;
    private static Statement stmt = null;

    //Modify this string with your local database/schema name and username and password.
    private static String  DB_URL;

    //Modify this SELECT statement to fit your table name and columns, if you want to get some specific columns.
    private static final String MY_SELECT_STATEMENT = "SELECT * FROM customer order by id asc ;";

    //Singleton pattern.
    private static DbUtil instance = new DbUtil();

    public DbUtil() {
        Properties  properties = new Properties();
        //this will point to the database url in my package
        try(InputStream in = this.getClass().getClassLoader().getResourceAsStream("sample/properties/dbInfo.properties")){
            properties.load(in);
            DB_URL = properties.getProperty("dbUrl");

        }catch (IOException ex){
            ex.printStackTrace();
        }
        connect();
        setuptable();

    }


    public static DbUtil getInstance() {
        return instance;
    }

    private Connection connect() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }

   // this is method is to setup my book table to fetch
    public void setuptable() {
        String Table_Name = "book"; //we could return this...

        if (conn == null)
            conn = connect();

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
                            + "  book_id int(11) primary key, \n"
                            + "   ISBN varchar(45), \n"
                            + "  title varchar(45), \n"
                            + " author varchar(45), \n"
                            + " availablilty boolean default true "
                            + ")");


                }
            } catch (SQLException ex) {


                // handle any errors
                System.out.println("SQLException: " + ex.getMessage()+ "setupdatabase");
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

        }finally {

        }

    }
}

