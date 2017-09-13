package de.itech.borad.models;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Base64;

public class Invite extends BaseMessage {

    private String roomName;
    private byte[] preSharedKey;

    @Override
    public void parseMessage(JsonNode json){
        super.parseMessage(json);
        JsonNode data = getDataJson();
        if(!data.has("name") || !data.has("preSharedKey")){
            return;
        }
        roomName = getTrimmedStringFromJson(data, "name");
        preSharedKey = Base64.getDecoder().decode(getTrimmedStringFromJson(data, "preSharedKey"));
    }

    public byte[] getPreSharedKey() {
        return preSharedKey;
    }

    public String getRoomName() {
        return roomName;
    }
}
