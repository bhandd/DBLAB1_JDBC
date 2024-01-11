package dblab1.dblab1_jdbc.view;

//import se.kth.anderslm.booksdb.model.Book;
//import se.kth.anderslm.booksdb.model.BooksDbInterface;
//import se.kth.anderslm.booksdb.model.SearchMode;

import dblab1.dblab1_jdbc.model.*;
import dblab1.dblab1_jdbc.model.entityClasses.Book;
import dblab1.dblab1_jdbc.model.exceptions.BooksDbException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        try {
            if (searchFor != null && searchFor.length() > 1) {
                List<Book> result = new ArrayList<>();
                switch (mode) {
                    case Title:

                        // result = booksDb.searchBooksByTitleDB(searchFor);
                        result = BooksDb.searchDBBook("SELECT * FROM T_book WHERE title LIKE'%" + searchFor + "%'");

                        break;
                    case ISBN:
                        result = BooksDb.searchDBBook("SELECT * FROM T_book WHERE isbn LIKE'%" + searchFor + "%'");

                        break;
                    case Author:
                        result = BooksDb.searchDBBook("SELECT * FROM T_book WHERE author LIKE'%" + searchFor + "%'");

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
            BooksDbInterface booksDbInterface = new BooksDb();
            try {
                booksDbInterface.connect();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    };

//TODO: commented out for now. Already in getConnection. Should probably be moved to BooksDb

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
//                int bookId = rs.getInt("book_id");
//                String title = rs.getString("title");
//                String author = rs.getString("author");
//                String ISBN = rs.getString("ISBN");
//                int year = rs.getInt("year");
//
//                Book book = new Book(bookId, ISBN, title, year);
//                books.add(book);
//            }
//            System.out.println();
//        }
//    }

    public EventHandler<ActionEvent> showBooksInDB = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            //TODO:try to make methods to retrieve the connection and the books

           // Connection con = getConnection.getConnection();
            List<Book> books = new ArrayList<>();

            try(Connection con = BooksDb.shareConnection()) {
                //  getConnection.executeQuery(con, "SELECT * FROM T_book", books);
                BooksDb.executeQuery(/*con,*/ "SELECT * FROM T_book", books);
                // getConnection.searchBookDB("SELECT * FROM T_book"); //TODO: investigate if this is possible in some way
//                BooksDb.checkIfAuthorExists("Johan Larsson");
                booksView.displayBooks(books);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    };

    public EventHandler<ActionEvent> addBookDB = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            //TODO:try to make methods to retrieve the connection and the books

            // Connection con = getConnection.getConnection();
            List<Book> books = new ArrayList<>();

            try(Connection con = BooksDb.shareConnection()) {
                //  getConnection.executeQuery(con, "SELECT * FROM T_book", books);
                //   BooksDb.executeQuery(/*con,*/ "SELECT * FROM T_book", books);
                // getConnection.searchBookDB("SELECT * FROM T_book"); //TODO: investigate if this is possible in some way
          //      BooksDb.checkIfAuthorExists("Johan Larss");
                //   booksView.displayBooks(books);
                BooksDb.addBookToDb("9471324819234", "killen som hade en liten", "Fem Isex");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    };

    //Todo. Can be deleted
    /*public EventHandler<ActionEvent> updateBookDB1test = new EventHandler<ActionEvent>() {
       // String gradeValue = "2";
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        String gradeValue = null;
        String title = "mattebok";

        @Override
        public void handle(ActionEvent actionEvent) {
        //    BooksDb.setGrade(gradeValue, title);
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Two Input Alert");
            dialog.setHeaderText("Enter your details:");

            // Set up the first input field
            dialog.getDialogPane().setContentText("Title:");
            dialog.getEditor().setPromptText("Enter the name of book");

            // Add a second input field
            TextInputDialog anotherDialog = new TextInputDialog();
            anotherDialog.setTitle("Two Input Alert");
            anotherDialog.setHeaderText("Enter your details:");
            anotherDialog.getDialogPane().setContentText("Grade:");
            anotherDialog.getEditor().setPromptText("Enter new grade");
            Optional<String> resultName = dialog.showAndWait();
            if (resultName.isPresent()) {
                // If the first input is provided, show the second dialog
                Optional<String> resultAge = anotherDialog.showAndWait();

                // Check if the second input is provided
                if (resultAge.isPresent()) {
                    // Process the user's inputs
                    String title = resultName.get();
                    gradeValue = resultAge.get();
                    BooksDb.updateGrade(Integer.parseInt(gradeValue), String.valueOf(title));
                    // Display the results or perform further actions
                } else {
                    // User canceled the second input
                    System.out.println("Operation canceled by the user.");
                }
            } else {
                // User canceled the first input
                System.out.println("Operation canceled by the user.");
            }

        }

            //  booksView.displayBooks(books);

    };*/

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

            BooksDb.updateGrade(Integer.parseInt(gradeValue), String.valueOf(title));

            titleField.setText("");
            gradeField.setText("");
        }
    };

        //TODO. Can be deleted
//    public EventHandler<ActionEvent> searchDB = new EventHandler<ActionEvent>() {
//        @Override
//        public void handle(ActionEvent actionEvent) {
//            Connection con = getConnection.getConnection();
//            List<Book> books = new ArrayList<>();
//            try {
//                getConnection.executeQuery(con, "SELECT * FROM T_book", books);
//                getConnection.executeQuery(con, "SELECT * FROM T_book", books);
//                booksView.displayBooks(books);
//
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//        }
        //   };

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

