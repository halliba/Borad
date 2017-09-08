package de.itech.borad.client.chat;


import de.itech.borad.core.MessageController;
import de.itech.borad.models.ChatMessage;
import de.itech.borad.network.UdpManager;
import javafx.scene.control.TextField;

public class ChatInput extends TextField {

    MessageController controller;

    public ChatInput(ChatPanel chatPanel, MessageController controller){
        super();
        this.controller = controller;
        this.getStyleClass().add("chatInput");
        this.setOnAction(e -> {
            // add your code to be run here
            System.out.println(e);
            ChatInput input = ((ChatInput) e.getTarget());
            String message = input.getText();
            chatPanel.addMessage(new ChatMessage("Sergej", message));
            controller.sendMessage(message);
            input.setText("");
        });
    }

}
