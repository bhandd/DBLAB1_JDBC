/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dblab1.dblab1_jdbc.model;

import dblab1.dblab1_jdbc.model.entityClasses.Author;
import dblab1.dblab1_jdbc.model.entityClasses.Book;
import dblab1.dblab1_jdbc.model.exceptions.BooksDbException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
//TODO: make all SQL-queries to prepared statements to prevent SQL-incetion

/**
 * A mock implementation of the BooksDBInterface interface to demonstrate how to
 * use it together with the user interface.
 * <p>
 * Your implementation must access a real database.
 *
 * @author anderslm@kth.se
 */
public class BooksDb implements BooksDbInterface {
    private final List<Book> books;
    public BooksDb() {
        books = List.of();
    }
    /**
     * A class that represents a connection to a database.
     *
     *
     */
@Override
    public boolean connect() throws Exception {
        if (getConnection.StartConnection() != null) {
           // System.out.println("Yes");
            return true;
        } else {
          //  System.out.println("No");
            return false;
        }
    }

    /**
     * Closes an existing connection to the database.
     *
     * @throws BooksDbException if an error occurs during connection closure.
     * @throws SQLException if an error occurs during database interaction.
     */
    public void disconnect() throws BooksDbException, SQLException {
        getConnection.EndConnection();
    }

    public List<Book> getBookByAuthor(String name) throws SQLException, BooksDbException {
        int autId = getAuthorIdByName(name);
        int bookId= getBookIdFromAuthorId(autId);
      return getBookFromID(bookId);
    }


    private int getAuthorIdByName(String name) throws RuntimeException{
        int authorId = -1;
        String query= "SELECT aut_id\n" +
                "FROM T_author\n" +
                "WHERE fullName ="+ name + ";";

        try (Statement stmt = getConnection.getConnection().createStatement()) {
            // Execute the SQL statement
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                authorId = rs.getInt("author_id");
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return authorId;
    }


//Todo: implementera
   private int getBookIdFromAuthorId(int authorId) throws RuntimeException{
       int bookId= -1;
        String query= "SELECT book_id\n" +
                "FROM book_author\n" +
                "WHERE author_id ="+ authorId + ";";
        try (Statement stmt = getConnection.getConnection().createStatement()) {
            // Execute the SQL statement
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
               bookId = rs.getInt("book_id");
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bookId;
    }

//TODO: implementera
private  List<Book> getBookFromID(int bookID) throws RuntimeException, BooksDbException, SQLException {
    String query = "SELECT *\n" +
            "FROM T_book\n" +
            "WHERE book_id =" + bookID + ";";

        List<Book> result = new ArrayList<>();
        ArrayList authors = new ArrayList<>();

        try (Statement stmt = getConnection.getConnection().createStatement()) {
            // Execute the SQL statement
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int bookId = rs.getInt("book_id");
                String ISBN = rs.getString("ISBN");
                String title = rs.getString("title");

                List<Integer> authorIds = getAuthorIdForBook(bookId);
                for (int i = 0; i < authorIds.size(); i++) {
                    authors.add(getAuthorById(authorIds.get(i)));
                }
                String author = rs.getString("author");
                Date published = rs.getDate("published");
                //   int pages = rs.getInt("pages");
                //  String language = rs.getString("language");
                String genre = rs.getString("genre");
                int grade = rs.getInt("grade");
                Book book = new Book(bookId, ISBN, title, author, published, genre, grade);
                //book.addAuthor(authors);
                //     System.out.println("Yes");
            }
            rs.close();
        } catch (SQLException e) {
            throw new BooksDbException(e.getMessage());
        }
        return result;

    }



    /**
     * Retrieves a list of author IDs for a specified book ID from the database.
     *
     * @param bookId The ID of the book for which to retrieve author IDs.
     * @return A list of author IDs for the specified book.
     * @throws RuntimeException If an error occurs during database interaction.
     */
    public static List<Integer> getAuthorIdForBook(int bookId) throws RuntimeException{
        List<Integer> authorIds = new ArrayList<>();
        String query= "SELECT author_id\n" +
                "FROM book_author\n" +
                "WHERE book_id ="+ bookId + ";";
//hämta alla author ID från databas
        try (Statement stmt = getConnection.getConnection().createStatement()) {
            // Execute the SQL statement
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                authorIds.add(rs.getInt("author_id"));
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return authorIds;
    }

    /**
     * Retrieves a list of authors for a specified author ID from the database.
     *
     * @param authorId The ID of the author for which to retrieve author information.
     * @return A list of `Author` objects for the specified author.
     * @throws RuntimeException If an error occurs during database interaction.
     */
    public static ArrayList<Author> getAuthorById(int authorId) throws RuntimeException{
        ArrayList<Author> authors =new ArrayList<>();
        String query= "SELECT * FROM T_author WHERE aut_id =" + authorId + ";";

//hämta alla author ID från databas
        try (Statement stmt = getConnection.getConnection().createStatement()) {
            // Execute the SQL statement
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                authors.add(new Author(rs.getInt("aut_id"), rs.getString("fullName")));
            }
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return authors;
    }


    @Override
    public List<Book> getBookList() throws SQLException {
        List<Book> books = new ArrayList<>();
        String query = "SELECT T_book.book_id, T_book.isbn, T_book.title, T_author.fullname, T_book.published, T_book.genre, T_book.grade FROM T_book INNER JOIN book_author ON T_book.book_id = book_author.book_id INNER JOIN T_author ON book_author.author_id = T_author.aut_id;";
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
                Book book = new Book(bookId, ISBN, title, author, published, genre, grade);
                System.out.println(book.toString());
                books.add(book);

            }
            System.out.println();
            return books ;
        }catch (SQLException e){
            throw new SQLException(e);
        }
    }




    /**
     * Searches the database for books matching a specified search query and returns a list of matching `Book` objects.
     *
     * @param query The search query to use to filter the books.
     * @return A list of matching `Book` objects.
     * @throws RuntimeException If an error occurs during database interaction.
     */

    @Override
    public List<Book> searchDBBook(String query, SearchMode mode ) throws BooksDbException {
        String column = mode.name();
        System.out.println(column);
        String searchString = "SELECT  book_id, isbn,  title, published, genre, grade FROM T_book WHERE " + column + " ='%" + query + "%';";
       // String searchString = "book_id, isbn, title, published, genre, grade FROM T_book WHERE title = 'katbok';";
      //  book_id, isbn,  title, published, genre, grade FROM T_book WHERE TITLE ='katbok'
        List<Book> result = new ArrayList<>();
       // ArrayList authors = new ArrayList<>();


        try (Statement stmt = getConnection.getConnection().createStatement()) {
            // Execute the SQL statement
            ResultSet rs = stmt.executeQuery(searchString);
            System.out.println("try statement");
            while (rs.next()) {
                System.out.println("hej");
                int bookId = rs.getInt("book_id");
                String ISBN = rs.getString("ISBN");
                String title = rs.getString("title");

//                List<Integer> authorIds = getAuthorIdForBook(bookId);
//                for(int i = 0; i < authorIds.size(); i++){
//                    authors.add(getAuthorById(authorIds.get(i)));
//                }
                String author = rs.getString("author");
                Date published = rs.getDate("published");
                //   int pages = rs.getInt("pages");
                //  String language = rs.getString("language");
                String genre = rs.getString("genre");
                int grade = rs.getInt("grade");
                Book book = new Book(bookId, ISBN, title,author, published, genre, grade);
                result.add(book);
               // book.addAuthor(authors);
                   System.out.println("Yes");
            }
            rs.close();
        } catch (SQLException e) {
            throw new BooksDbException(e.getMessage());
        }
        System.out.println(result.toString());
        return result;
    }

    /**
     * Retrieves the error count from the database for the specified query.
     *
     * @param query The SQL query for which to retrieve the error count.
     * @return The error count for the specified query.
     * @throws SQLException If an error occurs during database interaction.
     */
    public static int getErrorCount(String query) throws SQLException {
int errorCount = 0;
        try (Statement stmt = getConnection.getConnection().createStatement()) {
          //  System.out.println("current query to execute: " + query);
            // Execute the SQL statement
            ResultSet rs = stmt.executeQuery(query);
            //  Get the attribute names
            ResultSetMetaData metaData = rs.getMetaData();
            int ccount = metaData.getColumnCount();
            for (int c = 1; c <= ccount; c++) {
                System.out.print(metaData.getColumnName(c) + "\t");
            }
            System.out.println();
                rs.next();
                errorCount = rs.getInt("@@error_count");
           // System.out.println("executed a query");
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }
        return  errorCount;
    }


    /**
     * Executes the specified SQL statement against the database.
     *
     * @param statement The SQL statement to execute.
     * @throws SQLException If an error occurs during database interaction.
     */
    public static void executeStatement(String statement) throws SQLException {
      //  System.out.println("current statement to execute: " +statement);
        try (Statement stmt = getConnection.getConnection().createStatement()) {
            // Execute the SQL statement
            stmt.executeUpdate(statement);
          //  System.out.println("executed a statement");
        }catch (SQLException e){
            System.err.println(e.getMessage());
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

        try (var stmt = getConnection.getConnection().prepareStatement(sql)) {
            stmt.setString(2, title);
            stmt.setInt(1, grade);

            // execute the update
            int rowAffected = stmt.executeUpdate();
            System.out.println("Row affected " + rowAffected);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * Adds a new book to the database with the specified ISBN, title, genre, author's full name, publication date, and grade.
     *
     * @param isbn The ISBN of the book to be added.
     * @param title The title of the book to be added.
     * @param genre The genre of the book to be added.
     * @param fullName The full name of the author of the book to be added.
     * @param publish The date of publication of the book to be added.
     * @param grade The grade level of the book to be added.
     * @throws SQLException If an error occurs during database interaction.
     */
    @Override
    public void addBook(String isbn, String title, String genre, String fullName, Date publish, String grade) throws SQLException {
        try(Statement stmt = getConnection.getConnection().createStatement()){
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
            System.out.println("bokID: "+ currentBook_id +"AutID: " + currentAut_id);

            getConnection.getConnection().setAutoCommit(false);

            if (!authorExists(fullName)) {
                executeStatement("INSERT INTO T_Author (fullName) VALUES ( '" + fullName + "');");
                System.out.println("Author" + fullName + "added!");
            }
            executeStatement("INSERT INTO T_book (isbn, title, genre, published, grade) VALUES ('" + isbn + "' ,'" + title + "' ,'" + genre + "' ,'" + publish +  "' ,'" + grade + "' );");
        System.out.println("added" + isbn +","+ title+ ","+ genre + "To book");
            //gör både lägg till T_book och lägg till book_id, aut_id i book_autho
            executeStatement("INSERT INTO book_author (book_id, author_id) VALUES (" + currentBook_id  + ","  + currentAut_id + " );");
            System.out.println("wtf! This is incredible!");


        }catch(SQLException e){
            System.out.println("Ett fel inträffade i addBook: " + e.getMessage());
        }finally {
            // Kontrollera om det finns några fel
            int errorCount = getConnection.getConnection().getTransactionIsolation();
            int realErrorCount = getErrorCount(" SELECT @@error_count;");
           // System.out.println("Real error count: " + realErrorCount);
         //   System.out.println("Dont know what error count this is?: " + errorCount);

            if (realErrorCount != 0) {
                // Gör en rollback
                getConnection.getConnection().rollback();
                getConnection.getConnection().setAutoCommit(true);
                System.out.println("Rollback");
            } else {
                // Gör en commit
                //getConnection.getConnection().commit();
                executeStatement("commit;");
                getConnection.getConnection().setAutoCommit(true);
                System.out.println("Changes commited to database");
            }
        }
    }


    /**
     * Deletes a book from the database with the specified title.
     *
     * @param title The title of the book to be deleted.
     * @throws SQLException If an error occurs during database interaction.
     */
    public static void deleteBook(String title) throws SQLException {

try{
    getConnection.getConnection().setAutoCommit(false);

        executeStatement("DELETE FROM book_author WHERE book_id IN (SELECT book_id FROM T_book WHERE title = '" + title + "');");
       // System.out.println("Deleted in book_author");
        executeStatement("DELETE FROM T_book WHERE title = '"+ title + "' and book_id <> 0;");

    }catch(SQLException e){
    System.out.println("Ett fel inträffade i deleteBook: " + e.getMessage());
    }finally {
    // Kontrollera om det finns några fel
    int errorCount = getConnection.getConnection().getTransactionIsolation();
    int realErrorCount = getErrorCount(" SELECT @@error_count;");
    System.out.println("Real error count: " + realErrorCount);
    System.out.println("Dont know what error count this is?: " + errorCount);

    if (realErrorCount != 0) {
        // Gör en rollback
        getConnection.getConnection().rollback();
        getConnection.getConnection().setAutoCommit(true);
        System.out.println("Rollback");
    } else {
        // Gör en commit
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

    String query = "SELECT COUNT(*) FROM T_author WHERE fullName ='" + author + "'";
    Connection con = getConnection.getConnection();
    try (Statement stmt = con.createStatement()) {
        // Execute the SQL statement
        ResultSet rs = stmt.executeQuery(query);
        rs.next();
        int count = rs.getInt(1);
        if (count > 0){
        //    System.out.println(author + " exists in DB!");
            return true;
        }
    } catch (SQLException e) {
        System.err.println(e.getMessage());
    }
//   System.out.println(author + " does not exist in DB!");
    return false;
}

}
