package de.itech.borad.models;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Base64;

public class Invite extends BaseMessage {

    private String roomName;
    private byte[] preSharedKey;

    @Override
    public void parseMessage(JsonNode json){

    }

    public byte[] getPreSharedKey() {
        return preSharedKey;
    }

    public String getRoomName() {
        return roomName;
    }
}
