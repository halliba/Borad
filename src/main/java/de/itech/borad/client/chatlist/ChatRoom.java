package de.itech.borad.client.chatlist;

import de.itech.borad.core.CryptUtils;
import de.itech.borad.core.HashUtils;


public class ChatRoom {

    private String name;
    private byte[] preSharedKey;

    public ChatRoom(String name, String password){
        this.name = name;
        this.preSharedKey = HashUtils.hashSHA256(password);
    }

    public ChatRoom(String name, byte[] preSharedKey){
        this.name = name;
        this.preSharedKey = preSharedKey;
    }

    public boolean isRoom(byte[] chatRoomName){
        byte[] encrypted = CryptUtils.aesDecryptByteArray(chatRoomName, this.preSharedKey);
        return new String(encrypted).equals(name);
    }

    public byte[] getPreSharedKey(){
        return preSharedKey;
    }
}
