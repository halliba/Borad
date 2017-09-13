package de.itech.borad.models;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Base64;

public class Message extends BaseMessage{

    private String text;
    private byte[] chatRoom;

    @Override
    public void parseMessage(JsonNode json){
        super.parseMessage(json);
        JsonNode data = getDataJson();

        if(!data.has("content") || !data.has("chatRoom")){
            return;
        }
        text = getTrimmedStringFromJson(json, "content");
        chatRoom = Base64.getDecoder().decode(getTrimmedStringFromJson(json, "chatRoom"));
    }

    public static Message buildTestMessage(){
        Message message = new Message();
        message.text = "This is the message content";
        message.setAuthor(new Author("Lara Lavendel", null));
        return message;
    }

    public byte[] getChatRoom() {
        return chatRoom;
    }

    public String getText() {
        return text;
    }
}
