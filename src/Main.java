import de.itech.borad.client.Gui;
import de.itech.borad.client.chatlist.PublicChatRoom;
import de.itech.borad.network.UdpManager;
import de.itech.borad.network.UdpServer;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main  extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        man.sendMessage("aölsdf{Hi:Hi}");
        new Gui(primaryStage);
    }

    static UdpManager man;

    public static void main(String[] args) {
        man = new UdpManager();
        PublicChatRoom publicChatRoom = new PublicChatRoom("Public","Public");
        launch(args);
    }
}
