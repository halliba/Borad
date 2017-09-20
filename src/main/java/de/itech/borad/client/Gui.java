package de.itech.borad.client;
import de.itech.borad.core.MessageController;
import de.itech.borad.models.Message;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;


public class Gui {
    private Stage window;
    private Scene scene;

    private static final int MIN_WIDTH = 800;
    private static final int MIN_HEIGHT = 600;

    private Right rightSide;

    private Left leftSide;

    private VBox chatList, userList;

    private Button selectedButton;

    public Gui(Stage window, MessageController controller) throws IOException {
        //initialise GUI
        this.window = window;

        rightSide = new Right(controller);

        leftSide = new Left(controller);

        leftSide.setRightSide(rightSide);

        HBox.setHgrow(rightSide, Priority.ALWAYS);
        HBox.setHgrow(leftSide, Priority.ALWAYS);
        HBox hBox = new HBox(leftSide,rightSide);

        this.scene = new Scene(hBox, MIN_WIDTH, MIN_HEIGHT);
        this.scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        window.setMinWidth(MIN_WIDTH);
        window.setMinHeight(MIN_HEIGHT);
        window.setScene(scene);
        window.setTitle("FlexMess");
        window.show();
    }

    public void addMessage(Message msg){
        Platform.runLater(() -> rightSide.addMessage(msg));
    }




}
