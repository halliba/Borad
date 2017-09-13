package de.itech.borad.client;

import de.itech.borad.client.chat.ChatInput;
import de.itech.borad.core.MessageController;
import de.itech.borad.models.BaseMessage;
import de.itech.borad.client.chat.ChatPanel;
import de.itech.borad.models.Message;
import javafx.geometry.Insets;
import javafx.scene.layout.*;



public class Right extends BorderPane {

    private ChatPanel chatPanel;

    public Right(MessageController controller){
        super();

        chatPanel = new ChatPanel();


        ChatInput input = new ChatInput(chatPanel, controller);

        BorderPane.setMargin(chatPanel, new Insets(0,0,10,0));
        BorderPane.setMargin(input, new Insets(0,10,10,10));

        this.getStyleClass().add("right");
        this.setCenter(chatPanel);
        this.setBottom(input);
    }

    public void addMessage(Message msg){
        chatPanel.addMessage(msg);
    }

}
