package de.itech.borad.client.chat;

import de.itech.borad.models.ChatMessage;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;


public class ChatPanel extends ScrollPane {
    private final VBox messages = new VBox(5);

    public ChatPanel() {
        this.getStyleClass().add("chat-panel");

        this.setContent(this.messages);
        this.setBackground(null);

    }

    /**
     * Add a message to this panel. The message is converted to a ChatBubble and added to the list of
     * children of this panel.
     * @param message The message to add to this panel.
     */
    public synchronized void addMessage(ChatMessage message) {
        if (message == null) throw new NullPointerException("The message to add to the panel can not be null");

        final ChatBubble bubble = new ChatBubble(message, false);
        bubble.prefWidthProperty().bind(this.widthProperty().subtract(11));

        this.messages.getChildren().add(bubble);

        this.messages.layout();
    }
}
