import java.sql.*;
import java.util.ArrayList;

/*
  This code was taken and adapted from the my teams solution for the team project module.
  Availability: GitLabs OHASTA
 */

/**
 * A class for using the database.
 */
public class Database {

    /**
     * Method to read from the database.
     * @param sql SQL query
     * @param conn Database connection
     * @return rs
     */
    public static ArrayList<ArrayList<String>> read(String sql, Connection conn) {
        try (Statement stm = conn.createStatement()){
            ResultSet rs = stm.executeQuery(sql);

            ArrayList<ArrayList<String>> results = new ArrayList<>();

            while (rs.next()){
                ArrayList<String> row = new ArrayList<>();
                int i = 1;
                int l = rs.getMetaData().getColumnCount();

                while (i <= l){
                    row.add(rs.getString(i));
                    i++;
                }

                results.add(row);
            }
            return results;

        } catch (SQLException e){
            System.out.println(e);
        }
        return null;
    }

    /**
     * Method to write to the database.
     * @param sql SQL query
     * @param conn Database connection
     */
    public static void write(String sql, Connection conn) {

        try (Statement stm = conn.createStatement()){
            stm.executeUpdate("START TRANSACTION;");

        } catch (SQLException e){
            System.out.println(e);
        }

        try (Statement stm = conn.createStatement()){
            stm.executeUpdate(sql);

        } catch (SQLException e){
            System.out.println(e);
        }

        try (Statement stm = conn.createStatement()){
            stm.executeUpdate("COMMIT;");

        } catch (SQLException e){
            System.out.println(e);
        }

    }

    /**
     * Method to connect to the database.
     */
    public static Connection connect() {
        Connection conn = null;

        try {

            // register MySQL Connector/J
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

        } catch (Exception ex) {

            System.out.println("Failed to register MySQL Connector/J");

        }

        try {

            conn = DriverManager.getConnection(
                    "jdbc:mysql://smcse-stuproj00.city.ac.uk/adbd214?user=adbd214&password=190024316");

        } catch (SQLException ex) {

            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());

        }
        return conn;
    }

    /**
     * Method to close the connection to the database.
     * @param conn Connection to the database
     */
    public static void closeConnection(Connection conn) throws SQLException {
        conn.close();
    }



}