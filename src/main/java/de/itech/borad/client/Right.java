package de.itech.borad.client;

import de.itech.borad.client.chat.ChatInput;
import de.itech.borad.core.MessageController;
import de.itech.borad.models.BaseMessage;
import de.itech.borad.client.chat.ChatPanel;
import de.itech.borad.models.Message;
import javafx.geometry.Insets;
import javafx.scene.layout.*;

import java.util.HashMap;


public class Right extends BorderPane {

    private ChatPanel chatPanel;

    private HashMap<String, ChatPanel> chatPanels;

    MessageController controller;

    public Right(MessageController controller){
        super();

        this.controller = controller;

        chatPanels = new HashMap<>();

        chatPanel = new ChatPanel(controller);

        chatPanels.put("Public", chatPanel);

        BorderPane.setMargin(chatPanel, new Insets(0,0,10,0));
        BorderPane.setMargin(chatPanel.getChatInput(), new Insets(0,10,10,10));

        this.getStyleClass().add("right");
        this.setCenter(chatPanel);
        this.setBottom(chatPanel.getChatInput());
    }

    public void addMessage(Message msg){
        String roomName = msg.getClearTextRoomName();
        ChatPanel panel = chatPanels.get(roomName);
        if(panel != null){
            panel.addMessage(msg);
        }
    }


    public boolean changeToChatPanel(String newPanelName){
        //return value is if a new panel is created
        ChatPanel newPanel = chatPanels.get(newPanelName);
        boolean isNew = false;
        if(newPanel != null){
            chatPanel = newPanel;
            isNew = false;
        } else {
            chatPanel = new ChatPanel(controller);
            chatPanels.put(newPanelName, chatPanel);
            isNew = true;
        }
        this.setCenter(chatPanel);
        this.setBottom(chatPanel.getChatInput());
        BorderPane.setMargin(chatPanel, new Insets(0,0,10,0));
        BorderPane.setMargin(chatPanel.getChatInput(), new Insets(0,10,10,10));
        return isNew;
    }

}
