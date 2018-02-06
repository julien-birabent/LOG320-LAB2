import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.Random;

public class UserInterface {

    private Stage mStage;

    private TextField inputParamN;
    private TextField inputParamX;
    private TextField inputParamY;
    private Button createGrid;
    private Button solveProblem;
    private Button resetProblem;
    private CheckBox showStep;
    private CheckBox drawGrid;

    public UserInterface(Stage mStage) {
        this.mStage = mStage;
        init();
    }

    private void init() {
        mStage.setTitle("Puzzle");

        VBox rootPane = new VBox();
        rootPane.setPadding(new Insets(10));
        rootPane.setSpacing(8);


        GridPane gridPane = addGrid(32);

        GridPane controlPanel = addControls();
        rootPane.getChildren().addAll(gridPane, controlPanel);
        rootPane.autosize();

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        Scene scene = new Scene(rootPane, screenBounds.getHeight() - screenBounds.getHeight() * 0.25, screenBounds.getHeight() - screenBounds.getHeight() * 0.25);
        mStage.setScene(scene);

        mStage.show();

        this.setupActionListeners();
    }

    private GridPane addControls() {
        GridPane controls = new GridPane();
        Insets controlInsets = new Insets(5, 12, 5, 12);

        //region param inputs setup
        Label labelParamN = new Label("N: ");
        Label labelParamX = new Label("X: ");
        Label labelParamY = new Label("Y: ");

        inputParamN = new TextField();
        inputParamX = new TextField();
        inputParamY = new TextField();

        HBox containerN = new HBox();
        containerN.getChildren().addAll(labelParamN, inputParamN);
        containerN.setPadding(controlInsets);
        containerN.setSpacing(10);
        containerN.setAlignment(Pos.CENTER);

        HBox containerY = new HBox();
        containerY.getChildren().addAll(labelParamY, inputParamY);
        containerY.setPadding(controlInsets);
        containerY.setSpacing(10);
        containerY.setAlignment(Pos.CENTER);

        HBox containerX = new HBox();
        containerX.getChildren().addAll(labelParamX, inputParamX);
        containerX.setPadding(controlInsets);
        containerX.setSpacing(10);
        containerX.setAlignment(Pos.CENTER);

        VBox controlContainer = new VBox(containerN, containerX, containerY);
        controlContainer.setAlignment(Pos.CENTER);
        controlContainer.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: gray;");

        int buttonPrefSize = 200;

        createGrid = new Button("Créer une nouvelle grille");
        createGrid.setPadding(controlInsets);
        createGrid.setPrefWidth(buttonPrefSize);
        controlContainer.getChildren().add(createGrid);
        //endregion

        solveProblem = new Button("Résoudre");
        solveProblem.setPadding(controlInsets);
        solveProblem.setPrefWidth(buttonPrefSize);
        resetProblem = new Button("Reset");
        resetProblem.setPadding(controlInsets);
        resetProblem.setPrefWidth(buttonPrefSize);

        showStep = new CheckBox("Montrer les étapes");
        showStep.setPadding(controlInsets);
        showStep.setPrefWidth(buttonPrefSize);
        drawGrid = new CheckBox("Dessiner la grille");
        drawGrid.setPadding(controlInsets);
        drawGrid.setPrefWidth(buttonPrefSize);

        VBox actionContainer = new VBox(solveProblem, resetProblem, showStep, drawGrid);
        actionContainer.setSpacing(10);
        actionContainer.setAlignment(Pos.CENTER);
        actionContainer.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: gray;");

        controls.getChildren().addAll(controlContainer, actionContainer);

        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(50);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(50);
        controls.getColumnConstraints().addAll(column1, column2);

        GridPane.setConstraints(controlContainer, 0, 0);
        GridPane.setConstraints(actionContainer, 1, 0);

        return controls;
    }

    private GridPane addGrid(int n) {
        int SIZE = n;
        int length = SIZE;
        int width = SIZE;

        GridPane root = new GridPane();


        for (int y = 0; y < length; y++) {
            for (int x = 0; x < width; x++) {

                Random rand = new Random();
                int rand1 = rand.nextInt(2);

                // Create a new TextField in each Iteration
                TextField tf = new TextField();
               /* tf.setPrefHeight(50);
                tf.setPrefWidth(50);*/
                tf.setMinSize(5, 5);
                tf.setAlignment(Pos.CENTER);
                tf.setEditable(false);

                // Iterate the Index using the loops
                root.setRowIndex(tf, y);
                root.setColumnIndex(tf, x);
                root.getChildren().add(tf);
            }
        }
        return root;
    }

    private void setupActionListeners() {

        createGrid.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                event.consume();
                //TODO créer nouvelle grille
                int n, x, y;
                try {
                    n = Integer.valueOf(inputParamN.getCharacters().toString());
                    x = Integer.valueOf(inputParamX.getCharacters().toString());
                    y = Integer.valueOf(inputParamY.getCharacters().toString());

                } catch (Exception e) {
                    //TODO Handle
                }

            }
        });

        resetProblem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                event.consume();

                //TODO reset la grille
            }
        });

        solveProblem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                event.consume();

                //TODO : afficher résolution problème
            }
        });

        drawGrid.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                event.consume();

                //TODO dessiner/masquer grille
            }
        });

        showStep.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                event.consume();

                //TODO : show steps or not mode
            }
        });
    }


}


