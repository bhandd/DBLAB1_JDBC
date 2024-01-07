package dblab1.dblab1_jdbc.model;

import dblab1.dblab1_jdbc.model.entityClasses.Book;

import dblab1.dblab1_jdbc.model.entityClasses.Book;
import dblab1.dblab1_jdbc.model.exceptions.BooksDbException;

import java.sql.SQLException;
import java.util.List;

/**
 * This interface declares methods for querying a Books database.
 * Different implementations of this interface handles the connection and
 * queries to a specific DBMS and database, for example a MySQL or a MongoDB
 * database.
 *
 * NB! The methods in the implementation must catch the SQL/MongoDBExceptions thrown
 * by the underlying driver, wrap in a BooksDbException and then re-throw the latter
 * exception. This way the interface is the same for both implementations, because the
 * exception type in the method signatures is the same. More info in BooksDbException.java.
 * 
 * @author anderslm@kth.se
 */
public interface BooksDbInterface {
    
    /**
     * Connect to the database.
     *
     * @return true on successful connection.
     */
    public boolean connect() throws Exception;
    
    public void disconnect() throws SQLException;
    
    public List<Book> searchBooksByTitle(String title);

    List<Book> getDBList();

    // TODO: Add abstract methods for all inserts, deletes and queries 
    // mentioned in the instructions for the assignement.


    //TODO: kolla metoderna att de är rätt, (genererade av bard)
    // fler metoder kan behövas för de olika sök-frågorna tex SELECT * FROM T_author WHERE aut_id = 1 etc
    /**
     * Searches for books in the database.
     *
     * @param title The title of the book.
     * @param isbn The ISBN of the book.
     * @param author The name of the author.
     * @param rating The rating for the book (1-5).
     * @param genre The genre of the book.
     * @return A list of books that match the search criteria.
     */
    public List<Book> searchBooks(String title, String isbn, String author, int rating, String genre);

    /**
     * Rates a book.
     *
     * @param bookId The ID of the book to be rated.
     * @param rating The rating for the book (1-5).
     * @throws Exception If an error occurs during the update.
     */
    public abstract void rateBook(int bookId, int rating) throws Exception;



    /**
     * Adds a new book to the database. (Rating is not done here?)
     *
     * @param title The title of the book.
     * @param isbn The ISBN of the book.
     * @param genre The genre of the book.
     * @throws Exception If an error occurs during the insertion.
     */

    public void addBook(String title, String isbn, List<String> authors, String genre) throws Exception;



    /**
     * Adds a new author to the database.
     *
     * @param name The name of the author.
     * @throws Exception If an error occurs during the insertion.
     */
    public void addAuthor(String name) throws Exception;

    List<Book> searchBooksByTitleDB(String searchFor);

    List<Book> searchBooksByISBNDb(String searchFor);
}
