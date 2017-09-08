package de.itech.borad.client;

import de.itech.borad.client.chat.ChatInput;
import de.itech.borad.core.MessageController;
import de.itech.borad.models.ChatMessage;
import de.itech.borad.client.chat.ChatPanel;
import de.itech.borad.network.UdpManager;
import javafx.geometry.Insets;
import javafx.scene.layout.*;



public class Right extends BorderPane {

    private ChatPanel chatPanel;

    public Right(MessageController controller){
        super();

        chatPanel = new ChatPanel();
        for(int i = 0; i< 1; i++) {
            chatPanel.addMessage(new ChatMessage("Sergej", "Are we meeting today? Project has been already finished and I have results to show you."));
            chatPanel.addMessage(new ChatMessage("Anna", "Are we meeting today? Project has been already finished and I have results to show you."));
            chatPanel.addMessage(new ChatMessage("Sergej", "Are we meeting today?"));
        }

        ChatInput input = new ChatInput(chatPanel, controller);

        BorderPane.setMargin(chatPanel, new Insets(0,0,10,0));
        BorderPane.setMargin(input, new Insets(0,10,10,10));

        this.getStyleClass().add("right");
        this.setCenter(chatPanel);
        this.setBottom(input);
    }

    public void addMessage(ChatMessage msg){
        chatPanel.addMessage(msg);
    }

}
