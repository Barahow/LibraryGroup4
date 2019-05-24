package Login;

import java.sql.*;

public class DB {

    Connection connection;

    public DB(Connection conn) {
        this.connection = conn;
    }

    public Connection connDB() {
        try {
            //Class.forName("com.mysql.jdbc.Driver");
            //Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" +
            //        "library?user=root&password=root&useSSL=false\";");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library","root","root");
            return connection;
        } catch (Exception e) {
            //JOptionPane.showMessageDialog(null, e);
            e.printStackTrace();
            return null;
        }
    }

    public boolean signIn(String um,String pm) throws Exception {
        int count =0;
        String sqlMem = "SELECT * FROM login;";
        System.out.println("i am here");
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlMem);

            while(resultSet.next()){
                //System.out.println(resultSet.getString(1) + "  "+resultSet.getString(2));
                if(um.equals(resultSet.getString("username"))){
                    //System.out.println("Username was correct!");
                    if(pm.equals(resultSet.getString("passw"))){
                        //infoBox("Login Successful", null, "Success");
                        return true;
                    }else{
                        //pass not correct
                        //infoBox("pass not correct", null, "Failed");
                        return false;
                    }
                }else{
                    //infoBox("username not correct", null, "Failed");


               }

            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

  /*  public boolean signUp(String username, String password){

        try {
            Statement stateSignUp = connDB().createStatement();
            String sqlSign = "INSERT INTO login (username, passw) VALUES('username','password');";
            stateSignUp.executeUpdate(sqlSign);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }*/

}
