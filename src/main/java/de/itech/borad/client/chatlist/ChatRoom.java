package de.itech.borad.client.chatlist;

import de.itech.borad.core.HashUtils;

public class ChatRoom {

    private String name;
    private String passwordHash;

    public ChatRoom(String name, String passwordHash){
        this.name = name;
        this.passwordHash = passwordHash;
    }

}
