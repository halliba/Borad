package de.itech.borad.client;
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

    public Gui(Stage window) throws IOException {
        //inicializace GUI
        this.window = window;

        VBox left = new VBox();
        left.setStyle("-fx-background-color: #444753; -fx-text-fill: white;");
        left.setMinWidth(260);
        left.setMaxWidth(260);


        Right rightSide = new Right();

        HBox.setHgrow(rightSide, Priority.ALWAYS);
        HBox.setHgrow(left, Priority.ALWAYS);
        HBox hBox = new HBox(left,rightSide);

        this.scene = new Scene(hBox, MIN_WIDTH, MIN_HEIGHT);
        this.scene.getStylesheets().add("/static/style.css");
        window.setMinWidth(MIN_WIDTH);
        window.setMinHeight(MIN_HEIGHT);
        window.setScene(scene);
        window.setTitle("FlexMess");
        window.show();
    }
}
