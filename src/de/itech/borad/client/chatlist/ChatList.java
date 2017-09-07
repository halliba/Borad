package de.itech.borad.client.chatlist;

import javafx.scene.control.ListView;

public class ChatList<T extends ChatRoom>  extends ListView<T> {
    public ChatList() {
        setCellFactory(e -> new ChatRoomCell<T>());
    }
}
