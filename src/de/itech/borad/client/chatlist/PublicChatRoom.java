package de.itech.borad.client.chatlist;

import de.itech.borad.client.chat.ChatPanel;
import de.itech.borad.core.HashUtils;
import javafx.beans.property.LongProperty;
import javafx.beans.property.StringProperty;


public class PublicChatRoom implements ChatRoom  {

    private String name;
    private String password;
    private ChatPanel chatPanel;

    public PublicChatRoom(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getChatIdentifier(){
        return null;
    }

    private String getSHA256Password(){
        return HashUtils.hashSHA256(this.password);
    }

    @Override
    public LongProperty timestampProperty() {
        return null;
    }

    @Override
    public StringProperty nameProperty() {
        return null;
    }
}
