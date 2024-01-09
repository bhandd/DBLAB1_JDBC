package dblab1.dblab1_jdbc.view;

//import se.kth.anderslm.booksdb.model.Book;
//import se.kth.anderslm.booksdb.model.BooksDbInterface;
//import se.kth.anderslm.booksdb.model.SearchMode;

import dblab1.dblab1_jdbc.model.*;
import dblab1.dblab1_jdbc.model.entityClasses.Book;
import dblab1.dblab1_jdbc.model.exceptions.BooksDbException;
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

    private final BooksPaneView booksView; // view
    private final BooksDbInterface booksDb; // model

    public Controller(BooksDbInterface booksDb, BooksPaneView booksView) {
        this.booksDb = booksDb;
        this.booksView = booksView;
    }

    protected void onSearchSelected(String searchFor, SearchMode mode) {
        try {
            if (searchFor != null && searchFor.length() > 1) {
                List<Book> result = new ArrayList<>();
                switch (mode) {
                    case Title:

                        // result = booksDb.searchBooksByTitleDB(searchFor);
                        result = getConnection.searchBookDB("SELECT * FROM T_book WHERE title LIKE'%" + searchFor + "%'");

                        break;
                    case ISBN:
                        result = getConnection.searchBookDB("SELECT * FROM T_book WHERE isbn LIKE'%" + searchFor + "%'");

                        break;
                    case Author:
                        result = getConnection.searchBookDB("SELECT * FROM T_book WHERE author LIKE'%" + searchFor + "%'");

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
            BooksDbInterface booksDbInterface = new BooksDb();
            try {
                booksDbInterface.connect();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    };

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

    public EventHandler<ActionEvent> searchDB = new EventHandler<ActionEvent>() {
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
            BooksDbInterface booksDbInterface = new BooksDb();
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
