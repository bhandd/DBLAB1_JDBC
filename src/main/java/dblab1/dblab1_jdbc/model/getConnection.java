package dblab1.dblab1_jdbc.model;

import dblab1.dblab1_jdbc.model.entityClasses.Author;
import dblab1.dblab1_jdbc.model.entityClasses.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//TODO: should this be accessed trough the BooksDbInterface?
public class getConnection {

    private static Connection con = null;
    public static Connection StartConnection() throws Exception {
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
            return con;
            //executeQuery(con, "SELECT * FROM T_book");
        }  catch (SQLException e) {
            System.err.println("Connection failed. Error message: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static void EndConnection() throws SQLException {
        con.close();
        System.out.println("Connection closed.");
    }

    //TODO: Remove later
    //

//    public static void executeQuery(java.sql.Connection con, String query, List<Book> books) throws SQLException {
//
//        try (Statement stmt = con.createStatement()) {
//            // Execute the SQL statement
//            ResultSet rs = stmt.executeQuery(query);
//
//            // Get the attribute names
//            ResultSetMetaData metaData = rs.getMetaData();
//            int ccount = metaData.getColumnCount();
//            for (int c = 1; c <= ccount; c++) {
//                System.out.print(metaData.getColumnName(c) + "\t");
//            }
//            System.out.println();
//
//            // Get the attribute values
//            while (rs.next()) {
//              //  int bookId = rs.getInt("book_id");
//                String ISBN = rs.getString("ISBN");
//                String title = rs.getString("title");
//                /*
//                Author author = new Author();
//                author.setfName(rs.getString("author"));
//                */
////                int year = rs.getInt("year");
////                int grade = rs.getInt("grade");
////                int pages = rs.getInt("pages");
//               // String language = rs.getString("language");
////                int genreId = rs.getInt("genre id");
//
//                Book book = new Book(/*bookId,*/ ISBN, title);
//                books.add(book);
//            }
//            System.out.println();
//        }
//    }

    //TODO: Remove later

//    public static List<Book> searchBookDB( String query) {
//        List<Book> result = new ArrayList<>();
//
//        Connection con = getConnection.getConnection();
//        try (Statement stmt = con.createStatement()) {
//            // Execute the SQL statement
//            ResultSet rs = stmt.executeQuery(query);
//            while (rs.next()) {
//                //  int bookId = rs.getInt("book_id");
//                String ISBN = rs.getString("ISBN");
//                String title = rs.getString("title");
//                /*
//                Author author = new Author();
//                author.setfName(rs.getString("author"));
//                */
////                int year = rs.getInt("year");
////                int grade = rs.getInt("grade");
////                int pages = rs.getInt("pages");
//                // String language = rs.getString("language");
////                int genreId = rs.getInt("genre id");
//                int yearDB = rs.getInt("year");
//
//
////                titleDB = titleDB.toLowerCase();
////                if (titleDB.toLowerCase().contains(title)) {
//                Book book = new Book(/*bookId,*/ ISBN, title); result.add(book);
//                System.out.println("Yes");
//                // } else System.out.println("No");
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return result;
//    }


    //TODO: Temporary copy of searchBookDb, delete if not needed
/*
    public static List<Book> searchBookDB( String query) {
        List<Book> result = new ArrayList<>();

        Connection con = getConnection.getConnection();
        try (Statement stmt = con.createStatement()) {
            // Execute the SQL statement
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int bookIdDB = rs.getInt("book_id");
                String titleDB = rs.getString("title");
                Author authorDB = new Author();
                authorDB.setfName(rs.getString("author"));
                String ISBNDB = rs.getString("ISBN");
                int yearDB = rs.getInt("year");

//                titleDB = titleDB.toLowerCase();
//                if (titleDB.toLowerCase().contains(title)) {
                Book book = new Book(bookIdDB, ISBNDB, titleDB, authorDB, yearDB);
                result.add(book);
                System.out.println("Yes");
                // } else System.out.println("No");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

 */
    public static Connection getConnection() {
        return con;
    }
}
