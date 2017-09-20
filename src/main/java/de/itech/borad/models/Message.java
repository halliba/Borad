package de.itech.borad.models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.itech.borad.client.chatlist.ChatRoom;
import de.itech.borad.core.utils.CryptUtils;
import de.itech.borad.core.StateManager;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class Message extends BaseMessage{

    private String text;
    private byte[] chatRoomName;
    private String clearTextRoomName;

    @Override
    public void parseMessage(JsonNode json){
        if(!json.has("content") || !json.has("chatRoom")){
            return;
        }
        chatRoomName = Base64.getDecoder().decode(getTrimmedStringFromJson(json, "chatRoom"));
        StateManager stateManager = StateManager.getStateManager();
        ChatRoom room = stateManager.findChatRoom(chatRoomName);
        if(room != null){
            clearTextRoomName = room.getName();
            byte[] contentBytes = Base64.getDecoder().decode(getTrimmedStringFromJson(json, "content"));
            byte[] decryptedContent = CryptUtils.aesDecryptByteArray(contentBytes, room.getPreSharedKey());
            String content = new String(decryptedContent);
            ObjectNode node = (ObjectNode) json;
            node.put("content", Base64.getEncoder().encodeToString(content.getBytes()));
            super.parseMessage(node);
        }
    }

    public static Message buildTestMessage(){
        Message message = new Message();
        message.text = "This is the message content";
        message.setUser(new User("Lara Lavendel", null));
        return message;
    }

    public byte[] getChatRoomName() {
        return chatRoomName;
    }

    public String getClearTextRoomName(){
        return clearTextRoomName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text){
        this.text = text;
    }
}
