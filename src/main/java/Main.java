import de.itech.borad.client.Gui;
import de.itech.borad.client.chatlist.PublicChatRoom;
import de.itech.borad.core.MessageController;
import de.itech.borad.network.UdpManager;
import de.itech.borad.network.UdpServer;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main  extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        MessageController controller = new MessageController();
        Gui gui = new Gui(primaryStage, controller);
        controller.setGui(gui);
        controller.setManager(man);
        man.setMessageController(controller);
        man.startListening();
        man.sendMessage("FLEX{}");
    }

    static UdpManager man;

    public static void main(String[] args) {
        man = new UdpManager();
        PublicChatRoom publicChatRoom = new PublicChatRoom("Public","Public");
        launch(args);
    }
}
