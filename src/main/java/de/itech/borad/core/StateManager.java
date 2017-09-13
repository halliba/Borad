package de.itech.borad.core;

import de.itech.borad.client.chatlist.ChatRoom;
import de.itech.borad.models.KeepAlive;
import de.itech.borad.models.User;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Manages the state of all KeepAlives, Chatrooms etc.
 */

public class StateManager {

    private static StateManager stateManager;

    private ArrayList<ChatRoom> chatRooms;

    private HashMap<PublicKey, User> users;

    public static StateManager getStateManager(){
        if(stateManager == null){
            return new StateManager();
        }
        return stateManager;
    }

    private StateManager(){
        chatRooms = new ArrayList<>();
        chatRooms.add(new ChatRoom("Public", "Public"));

        users = new HashMap<>();
    }

    public ChatRoom findChatRoom(byte[] chatRoomName){
        for (ChatRoom room: chatRooms) {
            if(room.isRoom(chatRoomName)){
                return room;
            }
        }
        return null;
    }

    public User getUser(String name, String publicKeyPem){
        PublicKey publicKey = PemUtils.getPublicKeyFromPem(publicKeyPem.trim());
        if(users.containsKey(publicKey)){
            return users.get(publicKey);
        }
        User newUser = new User(name, publicKey);
        return newUser;
    }
}
