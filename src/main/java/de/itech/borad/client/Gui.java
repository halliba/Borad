package de.itech.borad.client;
import de.itech.borad.core.MessageController;
import de.itech.borad.models.ChatMessage;
import de.itech.borad.network.UdpManager;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;


public class Gui {
    private Stage window;
    private Scene scene;

    private static int MIN_WIDTH = 800;
    private static int MIN_HEIGHT = 600;

    private Right rightSide;

    public Gui(Stage window, MessageController controller) throws IOException {
        //initialise GUI
        this.window = window;

        VBox left = new VBox();
        left.setStyle("-fx-background-color: #444753; -fx-text-fill: white;");
        left.setMinWidth(260);
        left.setMaxWidth(260);


        rightSide = new Right(controller);

        HBox.setHgrow(rightSide, Priority.ALWAYS);
        HBox.setHgrow(left, Priority.ALWAYS);
        HBox hBox = new HBox(left,rightSide);

        this.scene = new Scene(hBox, MIN_WIDTH, MIN_HEIGHT);
        this.scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        window.setMinWidth(MIN_WIDTH);
        window.setMinHeight(MIN_HEIGHT);
        window.setScene(scene);
        window.setTitle("FlexMess");
        window.show();
    }

    public void addMessage(ChatMessage msg){
        Platform.runLater(() -> rightSide.addMessage(msg));
    }
}
