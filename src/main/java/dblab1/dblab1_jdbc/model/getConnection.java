package dblab1.dblab1_jdbc.model;

import dblab1.dblab1_jdbc.model.entityClasses.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class getConnection {

    private static Connection con = null;
    public static boolean StartConnection() throws Exception {

        String user = ("app_user");//args[0]; // user name
        String pwd = ("spion");//args[1]; // password
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


    public static List<Book> executeQuery(Connection con, String query) throws SQLException {
        List<Book> books = new ArrayList<>();
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
                int year = rs.getInt("year");

                Book book = new Book(bookId, ISBN, title, year);
                books.add(book);
            }
            System.out.println();
        }
        return books;
    }
    public static Connection getConnection() {
        return con;
    }
}
