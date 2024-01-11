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


//
    public BooksDb() {
      //  books = Arrays.asList(DATA); //TODO: ska troligtvis ta bort DATA
        books = List.of();
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
//TODO: sparas ifall den behövs sen.


    //Added by Anders
//    @Override
//    public List<Book> searchBooksByTitle(String searchTitle)
//            throws BooksDbException {
//        // mock implementation
//        // NB! Your implementation should select the books matching
//        // the search string via a query to a database (not load all books from db)
//        List<Book> result = new ArrayList<>();
//        searchTitle = searchTitle.toLowerCase();
//        for (Book book : books) {
//            if (book.getTitle().toLowerCase().contains(searchTitle)) {
//                result.add(book);
//            }
//        }
//        //spara i books<-SQL-fråga till databas
//      //  result.add(books);
//        return result;
//    }

    //TODO: Connection probably not needed as a parameter. Should be able to implement the search method here.

    public static void executeQuery(/*java.sql.Connection con,*/ String query, List<Book> books) throws SQLException {

        Connection con = getConnection.getConnection();
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
                String ISBN = rs.getString("ISBN");
                String title = rs.getString("title");
/*
                Author author = new Author();
                author.setfName(rs.getString("author"));
*/
                String author = rs.getString("Author");
                //String author = rs.getString("author");
                Date published = rs.getDate("year");
                //   int pages = rs.getInt("pages");
                //  String language = rs.getString("language");
                int genre_id = rs.getInt("genre_id");
                int grade = rs.getInt("grade");
                Book book = new Book(bookId, ISBN, title,author, published, genre_id, grade);
                System.out.println(book.toString());
                books.add(book);

            }
            System.out.println();
        }
    }

    public static List<Book> searchDBBook(String query) {
        List<Book> result = new ArrayList<>();

        Connection con = getConnection.getConnection();
        try (Statement stmt = con.createStatement()) {
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
                String author = rs.getString("Author");
                //String author = rs.getString("author");
                Date published = rs.getDate("year");
                //   int pages = rs.getInt("pages");
                //  String language = rs.getString("language");
                int genre_id = rs.getInt("genre_id");
                int grade = rs.getInt("grade");
                Book book = new Book(bookId, ISBN, title,author, published, genre_id, grade);
                System.out.println(book.toString());
                result.add(book);
                System.out.println(book.toString());
                System.out.println("Yes");
                // } else System.out.println("No");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    //TODO: antingen skriver man grade istället för column eller så skriver man
    // om metoden så man kan användan den för att komma åt valfri kolumn
    public static void setGrade(String gradeValue, String title){
        //TODO:try to make methods to retrieve the connection and the books

       // Connection con = getConnection.getConnection();
        List<Book> books = new ArrayList<>();

        try(Connection con = getConnection.getConnection()) {
            //  getConnection.executeQuery(con, "SELECT * FROM T_book", books);
          executeStatement("UPDATE T_book SET Grade= "+ gradeValue + "WHERE title = " + "'"+title+"'");
            // getConnection.searchBookDB("SELECT * FROM T_book"); //TODO: investigate if this is possible in some way

        } catch (SQLException e) {
            System.out.println("something went wrong");
            throw new RuntimeException(e);
        }

    }


    public static void executeStatement(String statement) throws SQLException {
            Connection con = getConnection.getConnection();



        try (Statement stmt = con.createStatement()) {
            // Execute the SQL statement
            int n = stmt.executeUpdate(statement);

         //   ResultSet rs = stmt.executeQuery(statement);

            // Get the attribute names
//            ResultSetMetaData metaData = rs.getMetaData();
//            int ccount = metaData.getColumnCount();
//            for (int c = 1; c <= ccount; c++) {
//                System.out.print(metaData.getColumnName(c) + "\t");
//            }
//            System.out.println();
////TODO: move the while loop in executeQuery, searchBookDB and this one to it´s own method
//            // Get the attribute values
//            while (rs.next()) {
//
//                int bookId = rs.getInt("book_id");
//                String ISBN = rs.getString("ISBN");
//                String title = rs.getString("title");
///*
//                Author author = new Author();
//                author.setfName(rs.getString("author"));
//*/
//                String author = rs.getString("Author");
//                //String author = rs.getString("author");
//                Date published = rs.getDate("year");
//                //   int pages = rs.getInt("pages");
//                //  String language = rs.getString("language");
//                int genre_id = rs.getInt("genre_id");
//                int grade = rs.getInt("grade");
//                Book book = new Book(bookId, ISBN, title,author, published, genre_id, grade);
//                System.out.println(book.toString());
              //  books.add(book);

          //  }
            System.out.println("");
        }
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

//    private static final Book[] DATA = {
//            new Book(1, "123456789", "Databases Illuminated", 2018),//new Date(2018, 1, 1)),
//            new Book(2, "234567891", "Dark Databases", 1990), //new Date(1990, 1, 1)),
//            new Book(3, "456789012", "The buried giant", 2000), //new Date(2000, 1, 1)),
//            new Book(4, "567890123", "Never let me go", 2000), //new Date(2000, 1, 1)),
//            new Book(5, "678901234", "The remains of the day", 2000), //new Date(2000, 1, 1)),
//            new Book(6, "234567890", "Alias Grace", 2000), //new Date(2000, 1, 1)),
//            new Book(7, "345678911", "The handmaids tale", 2010), //new Date(2010, 1, 1)),
//            new Book(8, "345678901", "Shuggie Bain", 2020), //new Date(2020, 1, 1)),
//            new Book(9, "345678912", "Microserfs", 2000) //new Date(2000, 1, 1)),
//    };


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

        try (var conn = getConnection.getConnection();
             var stmt = conn.prepareStatement(sql)) {

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

    public static void addBookToDb(String isbn, String title, String author) {
        var sql = "INSERT INTO T_book (isbn, title, author ) VALUES (?, ?, ?)";

        try (var conn = getConnection.getConnection();
             var stmt = conn.prepareStatement(sql)) {

            // prepare data for update
//            stmt.setString(1, title);
//            stmt.setInt(2, grade);
            stmt.setString(1, isbn);
            stmt.setString(2, title);
            stmt.setString(3, author);
        //    stmt.setInt(1, grade)

            //TODO: lägg till metoder:
           // checkIfAuthorExists();
          //  addAuthorInDB();
         //   updateBookAuthorInDB;

            // execute the update
            int rowAffected = stmt.executeUpdate();
            System.out.println("Row affected " + rowAffected);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**används för att kolla om en author existerar i T_book
     * används av metoden addBookToDB
     *
     *
     * */
public static boolean checkIfAuthorExists(String author){
        //TODO: använd en author här istället för en String?
    String query = "SELECT COUNT(*) FROM T_book WHERE author ='" + author + "'";
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
