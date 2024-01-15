package dblab1.dblab1_jdbc.view;

//import se.kth.anderslm.booksdb.model.Book;
//import se.kth.anderslm.booksdb.model.BooksDbInterface;
//import se.kth.anderslm.booksdb.model.SearchMode;

import dblab1.dblab1_jdbc.model.*;
import dblab1.dblab1_jdbc.model.entityClasses.Book;
import dblab1.dblab1_jdbc.model.entityClasses.Genre;
import dblab1.dblab1_jdbc.model.exceptions.BooksDbException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

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
    private BooksPaneView booksView; // view
    private BooksDbInterface booksDb; // model

    public Controller(BooksDbInterface booksDb, BooksPaneView booksView) {
        this.booksDb = booksDb;
        this.booksView = booksView;
    }

    protected void onSearchSelected(String searchFor, SearchMode mode) {
        String searchTitle = ("SELECT b.book_id, b.isbn,  b.title, a.fullName, b.published, b.genre, b.grade\n" +
                "FROM T_book b \n" +
                "INNER JOIN book_author ba \n" +
                "ON b.book_id = ba.book_id \n" +
                "INNER JOIN T_author a \n" +
                "ON ba.author_id = a.aut_id WHERE b.title LIKE '%" + searchFor + "%';");

        String searchISBN = ("SELECT b.book_id, b.isbn,  b.title, a.fullName, b.published, b.genre, b.grade\n" +
                "FROM T_book b \n" +
                "INNER JOIN book_author ba \n" +
                "ON b.book_id = ba.book_id \n" +
                "INNER JOIN T_author a \n" +
                "ON ba.author_id = a.aut_id WHERE b.isbn LIKE '%" + searchFor + "%';");

        String searchAuthor = ("SELECT b.book_id, b.isbn,  b.title, a.fullName, b.published, b.genre, b.grade\n" +
                "FROM T_book b \n" +
                "INNER JOIN book_author ba \n" +
                "ON b.book_id = ba.book_id \n" +
                "INNER JOIN T_author a \n" +
                "ON ba.author_id = a.aut_id WHERE a.fullName LIKE '%" + searchFor + "%';");
        try {
            if (searchFor != null && searchFor.length() > 1) {
                List<Book> result = new ArrayList<>();
                switch (mode) {
                    case Title:
                        result = BooksDb.searchDBBook(searchTitle);
                        break;
                    case ISBN:
                        result = BooksDb.searchDBBook(searchISBN);
                        break;
                    case Author:
                        result = BooksDb.searchDBBook(searchAuthor);
                        break;
                    default:
                        result = new ArrayList<>();
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
            booksView.showAlertAndWait("Database error.", ERROR);
        }
    }


    public EventHandler<ActionEvent> connectHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            try {
                booksDb.connect();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    };


    public EventHandler<ActionEvent> showBooksInDB = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            String query = ("SELECT b.book_id, b.isbn, b.title, a.fullName, b.published, b.genre, b.grade\n" +
                    "FROM T_book b INNER JOIN book_author ba ON b.book_id = ba.book_id INNER JOIN T_author a ON ba.author_id = a.aut_id;");
            List<Book> books = new ArrayList<>();
            try {
               BooksDb.executeQuery(/*con,*/query, books);
                booksView.displayBooks(books);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    };

    public EventHandler<ActionEvent> addBookDB = new EventHandler<ActionEvent>() {
        Alert alert = new Alert(CONFIRMATION);

        String isbn = null;
        String title = null;

        String author = null;

        String published = null;

        String genre = null;
        String grade = null;

        private TextField titleField = new TextField();

        private TextField isbnFiled = new TextField();

        private TextField authorFiled = new TextField();

        private TextField publishedFiled = new TextField();

        private TextField gradeField = new TextField();

        private ComboBox<Genre> gradeComboBox = new ComboBox<>();

        @Override
        public void handle(ActionEvent actionEvent) {

            // Connection con = getConnection.getConnection();
            List<Book> books = new ArrayList<>();
            alert.setTitle("Add book");
            alert.setResizable(false);

            GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setHgap(5);
            grid.setVgap(5);
            grid.setPadding(new Insets(10, 10, 10, 10));
            grid.add(new Label("Isbn for book "), 1, 1);
            grid.add(isbnFiled, 2, 1);
            grid.add(new Label("Enter title of book "), 1, 2);
            grid.add(titleField, 2, 2);
            grid.add(new Label("Author that wrote book "), 1, 3);
            grid.add(authorFiled, 2, 3);
            grid.add(new Label("Add publish date "), 1, 4);
            grid.add(publishedFiled, 2, 4);
            grid.add(new Label("Chose genre to book "), 1, 5);
            grid.add(gradeComboBox, 2, 5);
            grid.add(new Label("Set grad to book"), 1, 6);
            grid.add(gradeField, 2, 6);

            gradeComboBox.getItems().addAll(Genre.values());
            alert.getDialogPane().setContent(grid);
            alert.showAndWait();

            isbn = isbnFiled.getText();
            title = titleField.getText();
            //genre = titleField.getText();
            author = authorFiled.getText();
            genre = String.valueOf(gradeComboBox.getValue());
            published = publishedFiled.getText();
            grade = gradeField.getText();
            new Thread(() -> {
                try {
                    // Database code to add a book
                    booksDb.addBook(isbn, title, genre, author, Date.valueOf(published), grade);
                    // Update UI components with data using Platform.runLater()
                    Platform.runLater(() -> {
                        isbnFiled.setText("");
                        titleField.setText("");
                        authorFiled.setText("");
                        publishedFiled.setText("");
                        gradeField.setText("");
                        gradeComboBox.setItems(FXCollections.observableArrayList());
                    });
                } catch (Exception e) {
                    System.out.println("An error occurred in handle addBookDB: " + e.getMessage());
                }
            }).start();
        }
    };


    public EventHandler<ActionEvent> updateBookDB = new EventHandler<ActionEvent>() {
        // String gradeValue = "2";
        Alert alert = new Alert(CONFIRMATION);
        String gradeValue = null;
        String title = "mattebok";

        private TextField titleField = new TextField();
        private TextField gradeField = new TextField();

        @Override
        public void handle(ActionEvent actionEvent) {
            alert.setTitle("Set new grade");
            alert.setResizable(false);

            GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setHgap(5);
            grid.setVgap(5);
            grid.setPadding(new Insets(10, 10, 10, 10));
            grid.add(new Label("Title for book "), 1, 1);
            grid.add(titleField, 2, 1);
            grid.add(new Label("New grade "), 1, 2);
            grid.add(gradeField, 2, 2);

            alert.getDialogPane().setContent(grid);
            alert.showAndWait();
            title = titleField.getText();
            gradeValue = gradeField.getText();
            new Thread(() -> {
                try {
                    BooksDb.updateGrade(Integer.parseInt(gradeValue), String.valueOf(title));
                    Platform.runLater(() -> {
                        titleField.setText("");
                        gradeField.setText("");
                    });
                } catch (NumberFormatException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
    };

    public EventHandler<ActionEvent> deleteBookDB = new EventHandler<ActionEvent>() {
        private TextField titleField = new TextField();

        private Alert alert = new Alert(CONFIRMATION);

        private String title = null;

        @Override
        public void handle(ActionEvent actionEvent) {
            alert.setTitle("Delete");
            alert.setResizable(false);

            GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setHgap(5);
            grid.setVgap(5);
            grid.setPadding(new Insets(10, 10, 10, 10));
            grid.add(new Label("Title for book "), 1, 1);
            grid.add(titleField, 2, 1);

            alert.getDialogPane().setContent(grid);
            alert.showAndWait();
            title = titleField.getText();
            new Thread(() -> {
                try {
                    BooksDb.deleteBook(title);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                    //TODO. hantera på nåt bra vis
                }
                Platform.runLater(() -> {
                    titleField.setText("");
                });
            }).start();
        }
    };

    public EventHandler<ActionEvent> TitleSearch = new EventHandler<ActionEvent>() {
        private TextField titleField = new TextField();

        private Alert alert = new Alert(CONFIRMATION);

        private String title = null;

        @Override
        public void handle(ActionEvent actionEvent) {
            alert.setTitle("Search");
            alert.setResizable(false);

            GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setHgap(5);
            grid.setVgap(5);
            grid.setPadding(new Insets(10, 10, 10, 10));
            grid.add(new Label("Title for book "), 1, 1);
            grid.add(titleField, 2, 1);

            alert.getDialogPane().setContent(grid);
            alert.showAndWait();
            title = titleField.getText();

            onSearchSelected(title, SearchMode.Title);

            titleField.setText("");
        }
    };

    public EventHandler<ActionEvent> ISBNSearch = new EventHandler<ActionEvent>() {
        private TextField ISBNField = new TextField();

        private Alert alert = new Alert(CONFIRMATION);

        private String ISBN = null;

        @Override
        public void handle(ActionEvent actionEvent) {
            alert.setTitle("Search");
            alert.setResizable(false);

            GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setHgap(5);
            grid.setVgap(5);
            grid.setPadding(new Insets(10, 10, 10, 10));
            grid.add(new Label("Title for book "), 1, 1);
            grid.add(ISBNField, 2, 1);

            alert.getDialogPane().setContent(grid);
            alert.showAndWait();
            ISBN = ISBNField.getText();

            onSearchSelected(ISBN, SearchMode.ISBN);

            ISBNField.setText("");
        }
    };

    public EventHandler<ActionEvent> AuthorSearch = new EventHandler<ActionEvent>() {
        private TextField AuthorField = new TextField();

        private Alert alert = new Alert(CONFIRMATION);

        private String Author = null;

        @Override
        public void handle(ActionEvent actionEvent) {
            alert.setTitle("Search");
            alert.setResizable(false);

            GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setHgap(5);
            grid.setVgap(5);
            grid.setPadding(new Insets(10, 10, 10, 10));
            grid.add(new Label("Title for book "), 1, 1);
            grid.add(AuthorField, 2, 1);

            alert.getDialogPane().setContent(grid);
            alert.showAndWait();
            Author = AuthorField.getText();

            onSearchSelected(Author, SearchMode.Author);

            AuthorField.setText("");
        }
    };

    public EventHandler<ActionEvent> endConnectHandler = new EventHandler<ActionEvent>() {


        @Override
        public void handle(ActionEvent actionEvent) {
            try {
                BooksDbInterface.disconnect();
            } catch (SQLException | BooksDbException e) {
                throw new RuntimeException(e);
            }
        }
    };

    public void initSearchView (TextField searchField, ComboBox<SearchMode> searchModeBox) {
            String searchFor = searchField.getText();
            SearchMode mode = SearchMode.valueOf(String.valueOf(searchModeBox.getValue()));
            onSearchSelected(searchFor, mode);
        }
}
