import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    private UserInterface mUserInterface;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) {
        mUserInterface = new UserInterface(stage, new Controller());

        ProblemParameters.getInstance().addObserver(mUserInterface);
    }


}