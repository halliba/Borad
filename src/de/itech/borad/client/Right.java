package de.itech.borad.client;

import de.itech.borad.client.chat.ChatInput;
import de.itech.borad.models.ChatMessage;
import de.itech.borad.client.chat.ChatPanel;
import javafx.geometry.Insets;
import javafx.scene.layout.*;



public class Right extends BorderPane {

    public Right(){
        super();

        ChatPanel chatPanel = new ChatPanel();
        for(int i = 0; i< 1; i++) {
            chatPanel.addMessage(new ChatMessage("Sergej", "Are we meeting today? Project has been already finished and I have results to show you."));
            chatPanel.addMessage(new ChatMessage("Anna", "Are we meeting today? Project has been already finished and I have results to show you."));
            chatPanel.addMessage(new ChatMessage("Sergej", "Are we meeting today?"));
        }

        ChatInput input = new ChatInput(chatPanel);


        BorderPane.setMargin(chatPanel, new Insets(0,0,10,0));
        BorderPane.setMargin(input, new Insets(0,10,10,10));

        this.getStyleClass().add("right");
        this.setCenter(chatPanel);
        this.setBottom(input);
    }
}
