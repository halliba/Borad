import de.itech.borad.client.Gui;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main  extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        new Gui(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
