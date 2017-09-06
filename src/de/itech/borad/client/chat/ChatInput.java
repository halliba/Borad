package de.itech.borad.client.chat;


import de.itech.borad.models.ChatMessage;
import javafx.scene.control.TextField;

public class ChatInput extends TextField {

    public ChatInput(ChatPanel chatPanel){
        super();
        this.getStyleClass().add("chatInput");
        this.setOnAction(e -> {
            // add your code to be run here
            System.out.println(e);
            chatPanel.addMessage(new ChatMessage("sergej",((ChatInput) e.getTarget()).getText()));
        });
    }

}
