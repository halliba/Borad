package de.itech.borad.client.chat;

import de.itech.borad.core.KeyStore;
import de.itech.borad.core.MessageController;
import de.itech.borad.core.StateManager;
import de.itech.borad.models.BaseMessage;
import de.itech.borad.models.Message;
import de.itech.borad.models.User;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.security.Key;
import java.util.Date;


public class ChatPanel extends ScrollPane {
    private final VBox messages = new VBox(5);

    private ChatInput chatInput;


    public ChatPanel(MessageController controller) {

        chatInput = new ChatInput(this, controller);

        this.getStyleClass().add("chat-panel");

        this.setContent(this.messages);
        this.setBackground(null);

    }

    /**
     * Add a message to this panel. The message is converted to a ChatBubble and added to the list of
     * children of this panel.
     * @param message The message to add to this panel.
     */
    public synchronized void addMessage(Message message) {
        if (message == null) throw new NullPointerException("The message to add to the panel can not be null");

        final ChatBubble bubble = new ChatBubble(message, false);
        bubble.prefWidthProperty().bind(this.widthProperty().subtract(11));

        this.messages.getChildren().add(bubble);

        this.messages.layout();
    }

    public synchronized void addOwnMessage(String value){
        Message message = new Message();
        message.setUser(new User(StateManager.getStateManager().getOwnName(), KeyStore.getInstance().getPublicKey()));
        message.setText(value);
        message.setTimestamp(new Date());

        final ChatBubble bubble = new ChatBubble(message, true);
        bubble.prefWidthProperty().bind(this.widthProperty().subtract(11));

        this.messages.getChildren().add(bubble);

        this.messages.layout();

    }

    public ChatInput getChatInput() {
        return chatInput;
    }
}
