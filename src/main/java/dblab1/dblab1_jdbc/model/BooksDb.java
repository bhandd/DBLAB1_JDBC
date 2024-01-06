/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dblab1.dblab1_jdbc.model;

import dblab1.dblab1_jdbc.model.entityClasses.Book;
import dblab1.dblab1_jdbc.model.exceptions.BooksDbException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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

    private final List<Book> books;

    public BooksDb() {
        books = Arrays.asList(DATA);
    }

    @Override
    public boolean connect() throws Exception {
        if (getConnection.StartConnection()) {
            System.out.println("Yes");
            return true;
        } else {
            System.out.println("No");
            return false;
        }
    }

    @Override
    public void disconnect() throws BooksDbException, SQLException {
        getConnection.EndConnection();
    }

    @Override
    public List<Book> searchBooksByTitle(String searchTitle)
            throws BooksDbException {
        // mock implementation
        // NB! Your implementation should select the books matching
        // the search string via a query to a database (not load all books from db)
        List<Book> result = new ArrayList<>();
        searchTitle = searchTitle.toLowerCase();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(searchTitle)) {
                result.add(book);
            }
        }
        return result;
    }


    //TODO: kolla om vi ska ha en lista av böcker eller ett bokobjekt i taget
    public List<Book> searchBooks(String title, String isbn, String author, int rating, String genre){
        //TODO: make SQL-queries depending on different search criteria
        return null;

    }
    public void rateBook(int bookId, int rating) throws Exception{
        //TODO: rate a book in the database
    }

    public void addBook(String title, String isbn, List<String> authors, String genre) throws Exception{
        //TODO:  add a book to the database forst without author
        // add a book to the database WITH authro(Do this one last(but before addAuthor) as it accesses about 4 tables in the database and therefore is more complicated)
    }

    public void addAuthor(String name) throws Exception{
        //TODO: add an author to the database and at the same time update the book_author table to connect the author to the books and vice versa
    }

    private static final Book[] DATA = {
            new Book(1, "123456789", "Databases Illuminated", 2018),//new Date(2018, 1, 1)),
            new Book(2, "234567891", "Dark Databases", 1990), //new Date(1990, 1, 1)),
            new Book(3, "456789012", "The buried giant", 2000), //new Date(2000, 1, 1)),
            new Book(4, "567890123", "Never let me go", 2000), //new Date(2000, 1, 1)),
            new Book(5, "678901234", "The remains of the day", 2000), //new Date(2000, 1, 1)),
            new Book(6, "234567890", "Alias Grace", 2000), //new Date(2000, 1, 1)),
            new Book(7, "345678911", "The handmaids tale", 2010), //new Date(2010, 1, 1)),
            new Book(8, "345678901", "Shuggie Bain", 2020), //new Date(2020, 1, 1)),
            new Book(9, "345678912", "Microserfs", 2000) //new Date(2000, 1, 1)),
    };
}
