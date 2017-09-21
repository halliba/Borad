package de.itech.borad.core;

import de.itech.borad.client.Gui;
import de.itech.borad.client.chatlist.ChatRoom;
import de.itech.borad.core.utils.PemUtils;
import de.itech.borad.models.KeepAlive;
import de.itech.borad.models.User;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * Manages the state of all KeepAlives, Chatrooms etc.
 */

public class StateManager {

    private User me;

    private static StateManager stateManager;

    private ArrayList<ChatRoom> chatRooms;

    private ChatRoom selectedChatRoom;

    private HashMap<PublicKey, User> users;

    private ArrayList<UUID> messageIds;

    private KeyStore keyStore;

    private KeepAliveManager keepAliveManager;

    private Gui gui;

    public static StateManager getStateManager(){
        if(stateManager == null){
            stateManager = new StateManager();
            return stateManager;
        }
        return stateManager;
    }

    public void setKeepAliveManager(KeepAliveManager keepAliveManager){
        this.keepAliveManager = keepAliveManager;
    }

    public void setKeepAliveActive(boolean active){
        keepAliveManager.setActive(active);
    }

    public void setGui(Gui gui){
        this.gui = gui;
    }

    public ChatRoom getSelectedChatRoom(){
        return selectedChatRoom;
    }

    public void setSelectedChatroom(String name){
        for(ChatRoom room: chatRooms){
            if(room.getName().equals(name)){
                selectedChatRoom = room;
                return;
            }
        }
    }

    private StateManager(){
        chatRooms = new ArrayList<>();
        ChatRoom defaultChatRoom = new ChatRoom("Public", "Public");
        chatRooms.add(defaultChatRoom);
        selectedChatRoom = defaultChatRoom;
        users = new HashMap<>();
        messageIds = new ArrayList<>();
        keyStore = KeyStore.getInstance();
        me = new User(System.getProperty("user.name"), keyStore.getPublicKey());
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

    public void addMessage(UUID id){
        messageIds.add(id);
    }

    public boolean isNewMessage(UUID newId){
        return !messageIds.contains(newId);
    }

    public void handleKeepAlive(KeepAlive keepAlive){
        User user = users.get(keepAlive.getUser().getPublicKey());
        if(user == null){
            User keepAliveUser = keepAlive.getUser();
            keepAliveUser.receivedMessage();
            users.put(keepAliveUser.getPublicKey(), keepAliveUser);
        } else {
            user.receivedMessage();
        }
    }

    public void doUserUpdate(){
        users.forEach((key, user) -> {
            gui.doUserUpdate(user, user.isOnline());
        });
    }

    public String getOwnName(){
        return me.getName();
    }

    public void addChatRoom(ChatRoom room){
        chatRooms.add(room);
    }

    public boolean chatRoomExists(String name){
        for(ChatRoom room: chatRooms){
            if(room.getName().equals(name)){
                return true;
            }
        }
        return false;
    }
}
