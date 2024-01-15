package dblab1.dblab1_jdbc;

import dblab1.dblab1_jdbc.model.BooksDb;
import dblab1.dblab1_jdbc.model.BooksDbInterface;
import dblab1.dblab1_jdbc.model.entityClasses.Author;
import dblab1.dblab1_jdbc.model.entityClasses.Book;
import dblab1.dblab1_jdbc.model.entityClasses.testBook;
import dblab1.dblab1_jdbc.model.exceptions.BooksDbException;
import dblab1.dblab1_jdbc.view.BooksPaneView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


//import dbgui.lab1examplegui.model.BooksDbMockImpl;
//import dbgui.lab1examplegui.view.BooksPane;
//import se.kth.anderslm.booksdb.model.BooksDbMockImpl;
//import se.kth.anderslm.booksdb.view.BooksPane;

/**
 * Application start up.
 *
 * @author anderslm@kth.se
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        BooksDb booksDb = new BooksDb(); // model
        // Don't forget to connect to the db, somewhere...
        try {
            booksDb.connect();
            BooksPaneView root = new BooksPaneView(booksDb);

            Scene scene = new Scene(root, 800, 600);

            primaryStage.setTitle("Books Database Client");
            // add an exit handler to the stage (X) ?
            primaryStage.setOnCloseRequest(event -> {
                try {
                    booksDb.disconnect();
                } catch (Exception e) {
                }
            });
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void stop() throws Exception {
        BooksDbInterface.disconnect();
        super.stop();
    }
    public static void main(String[] args) {
       // launch(args);
        Author author1 = new Author(1, "kalle balle");
        Author author2 = new Author(2, "gurra murra");
        Author author3 = new Author(2, "gert Stjhärt");
        ArrayList<Author> authors = new ArrayList<>();
        authors.add(author1);
        authors.add(author2);
        authors.add(author3);
       // System.out.println(author.getFullName());
       // System.out.println(author.toString());
       // testBook testbook = new testBook(2,"255555","Drabant", author1, "ASDF", 3);
      //  author.getFullName();
        Date date = new Date(2000,01,01);
        Book testbook = new Book(2,"123123", "asdf",date ,"SCHOOL",5);
        testbook.addAuthor(authors);
       // testbook.printAuthors();
        System.out.println(testbook.getAuthorsNames(authors));
        System.out.println("färdig");

    }
}