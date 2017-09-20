package de.itech.borad.client.chat;


import de.itech.borad.client.Right;
import de.itech.borad.core.MessageController;
import javafx.scene.control.TextField;

public class ChatInput extends TextField {

    MessageController controller;

    public ChatInput(ChatPanel chatPanel, MessageController controller){
        super();
        this.controller = controller;
        this.getStyleClass().add("chatInput");
        this.setOnAction(e -> {
            ChatInput input = ((ChatInput) e.getTarget());
            String message = input.getText();
            chatPanel.addOwnMessage(message);
            controller.sendMessage(message);
            input.setText("");
        });
    }

}
