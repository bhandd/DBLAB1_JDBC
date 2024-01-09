package dblab1.dblab1_jdbc.model;

import dblab1.dblab1_jdbc.model.entityClasses.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class getConnection {

    private static Connection con = null;
    public static boolean StartConnection() throws Exception {

        String user = ("root");//args[0]; // user name
        String pwd = ("1234");//args[1]; // password
        System.out.println(user + ", *********");
        String database = "Library"; // the name of the specific database
        String server
                = "jdbc:mysql://localhost:3306/" + database
                + "?UseClientEnc=UTF8";
        try (Connection checkConnect = DriverManager.getConnection(server, user, pwd)){
            con = DriverManager.getConnection(server, user, pwd);
            //Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Connected!");
            return true;
            //executeQuery(con, "SELECT * FROM T_book");
        }  catch (SQLException e) {
            System.err.println("Connection failed. Error message: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static void EndConnection() throws SQLException {
        con.close();
        System.out.println("Connection closed.");
    }


    public static void executeQuery(java.sql.Connection con, String query, List<Book> books) throws SQLException {

        try (Statement stmt = con.createStatement()) {
            // Execute the SQL statement
            ResultSet rs = stmt.executeQuery(query);

            // Get the attribute names
            ResultSetMetaData metaData = rs.getMetaData();
            int ccount = metaData.getColumnCount();
            for (int c = 1; c <= ccount; c++) {
                System.out.print(metaData.getColumnName(c) + "\t");
            }
            System.out.println();

            // Get the attribute values
            while (rs.next()) {
                int bookId = rs.getInt("book_id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String ISBN = rs.getString("ISBN");
                Date year = rs.getDate("year");

                Book book = new Book(bookId, ISBN, title, (java.sql.Date) year);
                books.add(book);
            }
            System.out.println();
        }
    }
    public static List<Book> searchBookDB( String query) {
        List<Book> result = new ArrayList<>();

        Connection con = getConnection.getConnection();
        try (Statement stmt = con.createStatement()) {
            // Execute the SQL statement
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int bookIdDB = rs.getInt("book_id");
                String titleDB = rs.getString("title");
                String authorDB = rs.getString("author");
                String ISBNDB = rs.getString("ISBN");
                Date yearDB = rs.getDate("year");

//                titleDB = titleDB.toLowerCase();
//                if (titleDB.toLowerCase().contains(title)) {
                Book book = new Book(bookIdDB, ISBNDB, titleDB, (java.sql.Date) yearDB);
                result.add(book);
                System.out.println("Yes");
                // } else System.out.println("No");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
    public static Connection getConnection() {
        return con;
    }
}
