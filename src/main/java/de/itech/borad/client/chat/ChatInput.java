package de.itech.borad.client.chat;


import de.itech.borad.core.MessageController;
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
            controller.sendMessage(message);
            input.setText("");
        });
    }

}
