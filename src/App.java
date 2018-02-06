
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Random;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Hello World!");

        VBox rootPane = new VBox();

        GridPane g = addGrid(10);
        GridPane c = addControls();

        rootPane.getChildren().addAll(g,c);

        Scene scene = new Scene(rootPane, 1000, 1000);

        stage.setScene(scene);
        stage.show();
    }

    private GridPane addControls() {
        GridPane controls = new GridPane();
        Label label1 = new Label("N: ");
        Label label2 = new Label("X: ");
        Label label3 = new Label("Y: ");
        TextField tf1 = new TextField();
        TextField tf2 = new TextField();
        TextField tf3 = new TextField();


        controls.setConstraints(label1, 0, 0);
        controls.setConstraints(tf1, 1, 0);

        controls.setConstraints(label2, 0, 1);
        controls.setConstraints(tf2, 1, 1);

        controls.setConstraints(label3, 0, 2);
        controls.setConstraints(tf3, 1, 2);

        controls.getChildren().addAll(label1, tf1, label2, tf2, label3, tf3);

        return controls;
    }

    public GridPane addGrid(int n) {
        int SIZE = n;
        int length = SIZE;
        int width = SIZE;

        GridPane root = new GridPane();

        for(int y = 0; y < length; y++){
            for(int x = 0; x < width; x++){

                Random rand = new Random();
                int rand1 = rand.nextInt(2);

                // Create a new TextField in each Iteration
                TextField tf = new TextField();
                tf.setPrefHeight(50);
                tf.setPrefWidth(50);
                tf.setAlignment(Pos.CENTER);
                tf.setEditable(false);
                tf.setText("(" + rand1 + ")");

                // Iterate the Index using the loops
                root.setRowIndex(tf,y);
                root.setColumnIndex(tf,x);
                root.getChildren().add(tf);
            }
        }
        return root;
    }
}