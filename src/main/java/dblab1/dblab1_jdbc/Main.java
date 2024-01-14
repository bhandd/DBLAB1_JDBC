package dblab1.dblab1_jdbc;

import dblab1.dblab1_jdbc.model.BooksDb;
import dblab1.dblab1_jdbc.model.BooksDbInterface;
import dblab1.dblab1_jdbc.model.exceptions.BooksDbException;
import dblab1.dblab1_jdbc.view.BooksPaneView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;
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
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(() -> {
            BooksDb booksDb = new BooksDb(); // model
            // Don't forget to connect to the db, somewhere...
            Platform.runLater(() -> {
                try {
                    BooksDb.connect();
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
            });
        });

        executorService.execute(() -> {
            BooksDb booksDb = new BooksDb(); // model
            // Don't forget to connect to the db, somewhere...
            Platform.runLater(() -> {
                try {
                    BooksDb.connect();
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
            });
        });
        executorService.shutdown();
    }

    @Override
    public void stop() throws Exception {
        BooksDbInterface.disconnect();
        super.stop();
    }
    public static void main(String[] args) {
        launch(args);

    }
}