package de.itech.borad.client.chatlist;

import de.itech.borad.core.utils.CryptUtils;
import de.itech.borad.core.utils.HashUtils;

import java.io.UnsupportedEncodingException;


public class ChatRoom {

    public String getName() {
        return name;
    }

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

    public boolean isRoom(byte[] chatRoomName) {
        byte[] encrypted = CryptUtils.aesDecryptByteArray(chatRoomName, this.preSharedKey);
        try {
            return encrypted != null && new String(encrypted, "UTF-8").equals(name);
        } catch (UnsupportedEncodingException e) {
            return false;
        }
    }

    public byte[] getPreSharedKey(){
        return preSharedKey;
    }

    public byte[] getChatRoomIdentifier() throws UnsupportedEncodingException {
        return CryptUtils.aesEncryptByteArray(name.getBytes("UTF-8"), this.preSharedKey);
    }
}
