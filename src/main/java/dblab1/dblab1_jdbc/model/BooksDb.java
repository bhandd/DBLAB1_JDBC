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

    public BooksDb() {

        books = List.of();
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


    public static void executeQuery(/*java.sql.Connection con,*/ String query, List<Book> books) throws SQLException {

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
              //  Book book = new Book(bookId, ISBN, title,author, published, genre, grade);
               // System.out.println(book.toString());
              //  books.add(book);

            }
            System.out.println();
        }
    }

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

    public static ArrayList<Author> getAuthorById(int authorId) throws RuntimeException{
        ArrayList<Author> authors =new ArrayList<>();
        String query= "SELECT * FROM T_author WHERE id =" + authorId + ";";

//hämta alla author ID från databas
        try (Statement stmt = getConnection.getConnection().createStatement()) {
            // Execute the SQL statement
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                authors.add(new Author(rs.getInt("aut_id"), rs.getString("fullName")));
            }

            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return authors;

    }


    public static List<Book> searchDBBook(String query) {
        List<Book> result = new ArrayList<>();
        ArrayList authors = new ArrayList<>();


       // Connection con = getConnection.getConnection();
        try (Statement stmt = getConnection.getConnection().createStatement()) {
            // Execute the SQL statement
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                int bookId = rs.getInt("book_id");
                String ISBN = rs.getString("ISBN");
                String title = rs.getString("title");

                List<Integer> authorIds = getAuthorIdForBook(bookId);
            for(int i = 0; i < authorIds.size(); i++){
                authors.add(getAuthorById(authorIds.get(i)));
            }
                //author.setfName(rs.getString("author"));


                //String author = rs.getString("author");
                Date published = rs.getDate("published");
                //   int pages = rs.getInt("pages");
                //  String language = rs.getString("language");
                String genre = rs.getString("genre");
                int grade = rs.getInt("grade");
                Book book = new Book(bookId, ISBN, title, published, genre, grade);
                book.addAuthor(authors);
             //   System.out.println(book.toString());
             //   result.add(book);
              //  System.out.println(book.toString());
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
    public static int getErrorCount(String query) throws SQLException {
int errorCount = 0;
        try (Statement stmt = getConnection.getConnection().createStatement()) {
            System.out.println("current query to execute: " + query);
            // Execute the SQL statement

            ResultSet rs = stmt.executeQuery(query);

            //  Get the attribute names
            ResultSetMetaData metaData = rs.getMetaData();
            int ccount = metaData.getColumnCount();
            for (int c = 1; c <= ccount; c++) {
                System.out.print(metaData.getColumnName(c) + "\t");
            }
            System.out.println();
//TODO: move the while loop in executeQuery, searchBookDB and this one to it´s own method?
            // Get the attribute values
          //  while (rs.next()) {
                //  int bookId = rs.getInt("book_id");
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
            // Execute the SQL statement
            stmt.executeUpdate(statement);
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
//            executeStatement("START TRANSACTION;");
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

            // Kontrollera om det finns några fel
            int errorCount = getConnection.getConnection().getTransactionIsolation();
            int realErrorCount = getErrorCount(" SELECT @@error_count;");
            System.out.println("Real error count: " + realErrorCount);
            System.out.println("Dont know what error count this is?: " + errorCount);

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
        }catch(SQLException e){
            System.out.println("Ett fel inträffade i addBook: " + e.getMessage());
        }
    }

//TODO: lägg till kontroll att bok finns i DB
    public static void deleteBook(String title) throws SQLException {

try{
    getConnection.getConnection().setAutoCommit(false);

        executeStatement("DELETE FROM book_author WHERE book_id IN (SELECT book_id FROM T_book WHERE title = '" + title + "');");
        System.out.println("Deleted in book_author");
        //gör både lägg till T_book och lägg till book_id, aut_id i book_autho
        executeStatement("DELETE FROM T_book WHERE title = '"+ title + "' and book_id <> 0;");
        System.out.println("wtf! This is incredible!");

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
            //getConnection.getConnection().commit();
            executeStatement("commit;");
            getConnection.getConnection().setAutoCommit(true);
            System.out.println("Changes commited to database");
        }
    }catch(SQLException e){
    System.out.println("Ett fel inträffade i deleteBook: " + e.getMessage());
    }

    }

    //TODO: borde gå att köra denna med prepared statements om man redigerar den
    // se SQL-statements i metoden ovan

    /**
     *
     *
     * */
    public static void addBook1(String isbn, String title, String genre, String fullName) throws SQLException {
        var sql2 = "INSERT INTO T_book (isbn, title, genre) VALUES (?, ?, ?)";
        var sql1 = "INSERT INTO T_Author (fullName) VALUES (?)";
        var sql3 = "INSERT INTO book_author (book_id, author_id) VALUES ((SELECT book_id FROM t_book WHERE title = ' (?) ',(SELECT aut_id FROM t_author WHERE fullname = ' (?) '))";
        getConnection.getConnection().setAutoCommit(false); //TODO: lite osäker på om det är ok att sätta den här
        if (!authorExists(fullName)) {

            try (var stmt = getConnection.getConnection().prepareStatement(sql1)) {
                // Skapa författaren
                stmt.setString(1, fullName);
                stmt.executeUpdate();
                int rowAffected = stmt.executeUpdate();
                System.out.println("Row affected " + rowAffected);
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }


        //gör både lägg till T_book och lägg till book_id, aut_id i book_author
    try(var stmt2 = getConnection.getConnection().prepareStatement(sql2);) {
        // Lägg till boken
        stmt2.setString(1, isbn);
        stmt2.setString(2, title);
        stmt2.setString(3, genre);
        stmt2.executeUpdate();

    }
        try(var stmt3 = getConnection.getConnection().prepareStatement(sql3);) {
            // Lägg till boken
            stmt3.setString(1, isbn);
            stmt3.setString(2, title);
            stmt3.executeUpdate();
        }

    //lägg till book_id och aut_id i book_author
//   var stmt3 = getConnection.getConnection().prepareStatement(sql3);
//    stmt3.setString(1, title);
//    stmt3.setString(2, fullName);
//    stmt3.executeUpdate();
//    int rowAffected = stmt2.executeUpdate();
//    System.out.println("Row affected " + rowAffected);

    // Kontrollera om det finns några fel
    int errorCount = getConnection.getConnection().getTransactionIsolation();
    if (errorCount != 0) {
        // Gör en rollback
        getConnection.getConnection().rollback();
        getConnection.getConnection().setAutoCommit(true);
    } else {
        // Gör en commit
        getConnection.getConnection().commit();
        getConnection.getConnection().setAutoCommit(true);
    }

//}catch(SQLException e){
//    System.out.println("Ett fel inträffade i addBook: " + e.getMessage());
//}
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
