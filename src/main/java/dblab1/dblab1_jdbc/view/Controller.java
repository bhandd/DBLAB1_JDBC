package dblab1.dblab1_jdbc.view;

//import se.kth.anderslm.booksdb.model.Book;
//import se.kth.anderslm.booksdb.model.BooksDbInterface;
//import se.kth.anderslm.booksdb.model.SearchMode;

import dblab1.dblab1_jdbc.model.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static javafx.scene.control.Alert.AlertType.*;

/**
 * The controller is responsible for handling user requests and update the view
 * (and in some cases the model).
 *
 * @author anderslm@kth.se
 */
public class Controller {

    private final BooksPane booksView; // view
    private final BooksDbInterface booksDb; // model

    public Controller(BooksDbInterface booksDb, BooksPane booksView) {
        this.booksDb = booksDb;
        this.booksView = booksView;
    }

    protected void onSearchSelected(String searchFor, SearchMode mode) {
        try {
            if (searchFor != null && searchFor.length() > 1) {
                List<Book> result = null;
                switch (mode) {
                    case Title:
                        result = booksDb.searchBooksByTitle(searchFor);
                        break;
                    case ISBN:
                        // ...
                        break;
                    case Author:
                        // ...
                        break;
                    default:
                        result= new ArrayList<>();
                }
                if (result == null || result.isEmpty()) {
                    booksView.showAlertAndWait(
                            "No results found.", INFORMATION);
                } else {
                    booksView.displayBooks(result);
                }
            } else {
                booksView.showAlertAndWait(
                        "Enter a search string!", WARNING);
            }
        } catch (Exception e) {
            booksView.showAlertAndWait("Database error.",ERROR);
        }
    }

    public EventHandler<ActionEvent> connectHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            BooksDbInterface booksDbInterface = new BooksDbMockImpl();
            try {
                booksDbInterface.connect();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    };

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
                int year = rs.getInt("year");

                Book book = new Book(bookId, ISBN, title, year);
                books.add(book);
            }
            System.out.println();
        }
    }
    public EventHandler<ActionEvent> addBooksDB = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            Connection con = getConnection.getConnection();
            List<Book> books = new ArrayList<>();
            try {
                getConnection.executeQuery(con, "SELECT * FROM T_book", books);
                booksView.displayBooks(books);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    };

    public EventHandler<ActionEvent> endConnectHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            BooksDbInterface booksDbInterface = new BooksDbMockImpl();
            try {
                booksDbInterface.disconnect();
            } catch (SQLException | BooksDbException e) {
                throw new RuntimeException(e);
            }
        }
    };
    // TODO:
    // Add methods for all types of user interaction (e.g. via  menus).
}
