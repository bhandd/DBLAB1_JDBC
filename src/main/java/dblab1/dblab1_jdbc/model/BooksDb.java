/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dblab1.dblab1_jdbc.model;

import dblab1.dblab1_jdbc.model.entityClasses.Book;
import dblab1.dblab1_jdbc.model.exceptions.BooksDbException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A mock implementation of the BooksDBInterface interface to demonstrate how to
 * use it together with the user interface.
 * <p>
 * Your implementation must access a real database.
 *
 * @author anderslm@kth.se
 */
public class BooksDb implements BooksDbInterface {

   // private final List<Book> books;

    public BooksDb() {

    }


@Override
    public boolean connect() throws Exception {
        if (getConnection.StartConnection() != null) {
            System.out.println("Yes");
            return true;
        } else {
            System.out.println("No");
            return false;
        }
    }

    public static Connection shareConnection() {
        try{
            return getConnection.getConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void disconnect() throws BooksDbException, SQLException {
        getConnection.EndConnection();
    }


    @Override
    public void executeQuery(/*java.sql.Connection con,*/ String query, List<Book> books) throws SQLException {

      //  Connection con = getConnection.getConnection();
        try (Statement stmt = getConnection.getConnection().createStatement()) {
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
                String ISBN = rs.getString("ISBN");
                String title = rs.getString("title");
/*
                Author author = new Author();
                author.setfName(rs.getString("author"));
*/
                String author = rs.getString("fullName");
                //String author = rs.getString("author");
                Date published = rs.getDate("published");
                //   int pages = rs.getInt("pages");
                //  String language = rs.getString("language");
                String genre = rs.getString("genre");
                int grade = rs.getInt("grade");
                Book book = new Book(bookId, ISBN, title,author, published, genre, grade);
                System.out.println(book.toString());
                books.add(book);

            }
            System.out.println();
        }
    }

    @Override
    public List<Book> searchDBBook(String query) {
        List<Book> result = new ArrayList<>();

       // Connection con = getConnection.getConnection();
        try (Statement stmt = getConnection.getConnection().createStatement()) {
            // Execute the SQL statement
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int bookId = rs.getInt("book_id");
                String ISBN = rs.getString("ISBN");
                String title = rs.getString("title");
/*
                Author author = new Author();
                author.setfName(rs.getString("author"));
*/
                String author = rs.getString("fullName");
                //String author = rs.getString("author");
                Date published = rs.getDate("published");
                //   int pages = rs.getInt("pages");
                //  String language = rs.getString("language");
                String genre = rs.getString("genre");
                int grade = rs.getInt("grade");
                Book book = new Book(bookId, ISBN, title,author, published, genre, grade);
                System.out.println(book.toString());
                result.add(book);
                System.out.println(book.toString());
                System.out.println("Yes");
                // } else System.out.println("No");
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }




    //TODO: kan användas för att skapa ett objekt för alla variabler och returnera?
    public int getErrorCount(String query) throws SQLException {
int errorCount = 0;
        try (Statement stmt = getConnection.getConnection().createStatement()) {
            System.out.println("current query to execute: " + query);
            // Execute the SQL statement

            ResultSet rs = stmt.executeQuery(query);

            //  Get the attribute names
//            ResultSetMetaData metaData = rs.getMetaData();
//            int ccount = metaData.getColumnCount();
//            for (int c = 1; c <= ccount; c++) {
//                System.out.print(metaData.getColumnName(c) + "\t");
//            }
            System.out.println();

                rs.next();
                errorCount = rs.getInt("@@error_count");
        //    }
            System.out.println("executed a query");
        }catch (SQLException e){
            System.err.println("FUCK! Something went to shit again! "+ e.getMessage());
        }
return  errorCount;
    }

    public static void executeStatement(String statement) throws SQLException {
        System.out.println("current statement to execute: " +statement);

        try (Statement stmt = getConnection.getConnection().createStatement()) {

            stmt.executeUpdate(statement);


            System.out.println("executed a statement");
        }catch (SQLException e){
            System.err.println("FUCK! Something failed again! "+ e.getMessage());
        }
    }

    /**
     * Updates the grade for a book with the specified title.
     *
     * @param grade The new grade for the book.
     * @param title The title of the book to update.
     */
    public static void updateGrade(int grade, String title) {
        var sql = "UPDATE T_book "
                + "SET grade = ? "
                + "WHERE title = ?";
    //    Connection conn = getConnection.getConnection();
        try (var stmt = getConnection.getConnection().prepareStatement(sql)) {

            // prepare data for update
//            stmt.setString(1, title);
//            stmt.setInt(2, grade);
            stmt.setString(2, title);
            stmt.setInt(1, grade);

            // execute the update
            int rowAffected = stmt.executeUpdate();
            System.out.println("Row affected " + rowAffected);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void addBook(String isbn, String title, String genre, String fullName, Date publish, String grade) throws SQLException {
        try (Statement stmt = getConnection.getConnection().createStatement()) {
            // Execute the SQL statement
            ResultSet rs = stmt.executeQuery("SELECT MAX(book_id) AS currentBookID, MAX(aut_id) AS currentAuthorID\n" +
                    "FROM T_book\n" +
                    "LEFT JOIN T_author\n" +
                    "ON book_id = aut_id;;");

            ResultSetMetaData metaData = rs.getMetaData();
            int ccount = metaData.getColumnCount();
            for (int c = 1; c <= ccount; c++) {
                System.out.print(metaData.getColumnName(c) + "\t");
            }
            System.out.println();
            rs.next();
            int currentBook_id = rs.getInt("currentBookID") + 1;
            int currentAut_id = rs.getInt("currentAuthorID") + 1;
            System.out.println("bokID: " + currentBook_id + "AutID: " + currentAut_id);
//            executeStatement("START TRANSACTION;");
            getConnection.getConnection().setAutoCommit(false);

            if (!authorExists(fullName)) {
                executeStatement("INSERT INTO T_Author (fullName) VALUES ( '" + fullName + "');");
                System.out.println("Author" + fullName + "added!");
            }
            executeStatement("INSERT INTO T_book (isbn, title, genre, published, grade) VALUES ('" + isbn + "' ,'" + title + "' ,'" + genre + "' ,'" + publish + "' ,'" + grade + "' );");
            System.out.println("added" + isbn + "," + title + "," + genre + "To book");
            //gör både lägg till T_book och lägg till book_id, aut_id i book_autho
            executeStatement("INSERT INTO book_author (book_id, author_id) VALUES (" + currentBook_id + "," + currentAut_id + " );");
            System.out.println("wtf! This is incredible!");


        } catch (SQLException e) {
            System.out.println("Ett fel inträffade i addBook: " + e.getMessage());
        } finally {
            int realErrorCount = getErrorCount(" SELECT @@error_count;");
            if (realErrorCount != 0) {
                // Gör en rollback
                getConnection.getConnection().rollback();
                getConnection.getConnection().setAutoCommit(true);
            } else {
                // Gör en commit
                //getConnection.getConnection().commit();
                executeStatement("commit;");
                getConnection.getConnection().setAutoCommit(true);
                System.out.println("Changes commited to database");

            }

        }
    }


//TODO: lägg till kontroll att bok finns i DB
    public void deleteBook(String title) throws SQLException {

try{
    getConnection.getConnection().setAutoCommit(false);

        executeStatement("DELETE FROM book_author WHERE book_id IN (SELECT book_id FROM T_book WHERE title = '" + title + "');");
        System.out.println("Deleted in book_author");
        //gör både lägg till T_book och lägg till book_id, aut_id i book_autho
        executeStatement("DELETE FROM T_book WHERE title = '"+ title + "' and book_id <> 0;");
        System.out.println("wtf! This is incredible!");


    }catch(SQLException e){
    System.out.println("Ett fel inträffade i deleteBook: " + e.getMessage());
    }finally {
    int realErrorCount = getErrorCount(" SELECT @@error_count;");
    if (realErrorCount != 0) {
        // Gör en rollback
        getConnection.getConnection().rollback();
        getConnection.getConnection().setAutoCommit(true);
    } else {
        // Gör en commit
        //getConnection.getConnection().commit();
        executeStatement("commit;");
        getConnection.getConnection().setAutoCommit(true);
        System.out.println("Changes commited to database");

    }

}

    }


    /**används för att kolla om en author existerar i T_book
     * används av metoden addBookToDB
     *
     *
     * */
public static boolean authorExists(String author){
        //TODO: använd en author här istället för en String?
    String query = "SELECT COUNT(*) FROM T_author WHERE fullName ='" + author + "'";
    Connection con = getConnection.getConnection();
    try (Statement stmt = con.createStatement()) {
        // Execute the SQL statement
        ResultSet rs = stmt.executeQuery(query);
        rs.next();
        int count = rs.getInt(1);
        if (count > 0){
            System.out.println(author + " exists in DB!");
            return true;
        }
    } catch (SQLException e) {
        System.err.println(e.getMessage());
    }
   System.out.println(author + " does not exist in DB!");
    return false;
}



//TODO: spara tills vidare, försök att använda ett preparedStatement,
// vilket ska förhindra SQL-injection
    public static boolean checkIfAuthorExist(String author) {
        var sql = "SELECT * FROM T_book WHERE author = ?";


            // prepare data for update
//            stmt.setString(1, title);
//            stmt.setInt(2, grade);

        try (var con = getConnection.getConnection();
             var stmt = con.prepareStatement(sql)) {
            stmt.setString(1, author);
            // Execute the SQL statement
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            int count = rs.getInt(1);
            if (count > 0){
                System.out.println(author + " exists in DB!");
                return true;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        System.out.println(author + " does not exist in DB!");
        return false;

    }



}
