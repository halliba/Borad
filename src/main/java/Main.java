import com.fasterxml.jackson.databind.JsonNode;
import de.itech.borad.client.Gui;
import de.itech.borad.core.KeepAliveManager;
import de.itech.borad.core.MessageBuilder;
import de.itech.borad.core.MessageController;
import de.itech.borad.core.StateManager;
import de.itech.borad.network.UdpManager;
import javafx.application.Application;
import javafx.stage.Stage;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;


public class Main  extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        MessageController controller = new MessageController();
        Gui gui = new Gui(primaryStage, controller);
        controller.setGui(gui);
        controller.setManager(man);
        man.setMessageController(controller);
        man.startListening();
        KeepAliveManager keepAliveManager = new KeepAliveManager(controller);
    }

    static UdpManager man;

    public static void main(String[] args) {
        man = new UdpManager();
        Security.addProvider(new BouncyCastleProvider());
        launch(args);
    }
}
