import de.itech.borad.client.Gui;
import de.itech.borad.client.chatlist.PublicChatRoom;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main  extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        new Gui(primaryStage);
    }

    public static void main(String[] args) {
        PublicChatRoom publicChatRoom = new PublicChatRoom("Public","Public");
        launch(args);
    }
}
