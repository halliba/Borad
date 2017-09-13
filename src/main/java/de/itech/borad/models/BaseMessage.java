package de.itech.borad.models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.itech.borad.core.PemUtils;
import de.itech.borad.core.SignUtils;
import de.itech.borad.core.StateManager;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Date;

public class BaseMessage {

    private Date timestamp = new Date();
    private User user;
    private boolean isVerified;
    private JsonNode dataJson;

    public boolean isVerified() {
        return isVerified;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static KeepAlive parseKeepAlive(JsonNode json){
        KeepAlive keepAlive = new KeepAlive();
        keepAlive.parseMessage(json);
        return keepAlive;
    }

    public static Invite parseInvite(JsonNode json){
        Invite invite = new Invite();
        invite.parseMessage(json);
        return invite;
    }

    public static Message parseTextMessage(JsonNode json){
        Message message = new Message();
        message.parseMessage(json);
        return message;
    }

    public void parseMessage(JsonNode json){
        try{
            StateManager stateManager = StateManager.getStateManager();
            String rawContent = getTrimmedStringFromJson(json, "content");
            String content = getBase64AsUTF8(rawContent);
            JsonNode contentJson = getStringAsJson(content);
            if(!contentJson.has("data")){
                return;
            }
            String base64Data = getTrimmedStringFromJson(contentJson, "data");
            String data = getBase64AsUTF8(base64Data);
            JsonNode dataJson = getStringAsJson(data);
            this.dataJson = dataJson;
            String signature = getTrimmedStringFromJson(contentJson, "signature");
            if(!dataJson.has("sender")){
                return;
            }
            if(dataJson.has("content")){
                ((Message) this).setText(getTrimmedStringFromJson(dataJson, "content"));
            }
            JsonNode senderJson = getStringAsJson(dataJson.get("sender").toString());
            String name = getTrimmedStringFromJson(senderJson, "name");
            String publicKey = getTrimmedStringFromJson(senderJson, "publicKey");
            User user = stateManager.getUser(name, publicKey);
            user.receivedMessage();
            setUser(user);
            verify(base64Data, signature);
        } catch (NullPointerException ignored){}
    }

    static String getTrimmedStringFromJson(JsonNode json, String id){
        if(json.has(id)){
            return json.get(id).toString().replaceAll("\"", "");
        }
        throw new RuntimeException("can't find Field id: " + id + " in dataJson");
    }

    String getBase64AsUTF8(String base64){
        return new String(Base64.getDecoder().decode(base64));
    }

    JsonNode getStringAsJson(String string){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readTree(string);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    void verify(String data, String signature){
        try {
            isVerified = SignUtils.verify(data.getBytes("UTF-8"), Base64.getDecoder().decode(signature), user.getPublicKey());
        } catch (UnsupportedEncodingException e) {
            isVerified = false;
        }
    }

    public JsonNode getDataJson() {
        return dataJson;
    }
}
