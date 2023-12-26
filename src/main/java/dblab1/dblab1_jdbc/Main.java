package dblab1.dblab1_jdbc;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


import dblab1.dblab1_jdbc.view.Controller;
import dblab1.dblab1_jdbc.view.View;



import javafx.scene.control.MenuBar;
import javafx.scene.layout.VBox;
//todo
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Controller controller = new Controller();
        View gridView = new View();
        // MenuBar menuBar = View.getMenu();

        //  VBox root = new VBox(menuBar, gridView); //pane VBox kallad root
        VBox root = new VBox(gridView);
        Scene scene = new Scene(root);  //Lägger in pane root i scene
        stage.setScene(scene);
        stage.sizeToScene();
        stage.setResizable(false);
        stage.setTitle("Database-O-matic 40.000");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}