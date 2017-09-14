package de.itech.borad.core;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.itech.borad.client.Gui;
import de.itech.borad.client.chatlist.ChatRoom;
import de.itech.borad.models.Invite;
import de.itech.borad.models.KeepAlive;
import de.itech.borad.models.BaseMessage;
import de.itech.borad.models.Message;
import de.itech.borad.network.UdpManager;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

public class MessageController {

    private Gui gui;

    private UdpManager manager;

    public void setGui(Gui gui){
        this.gui = gui;
    }

    private StateManager stateManager;

    private MessageBuilder builder;

    public void setManager(UdpManager manager){
        this.manager = manager;
    }

    public MessageController(){
        stateManager = StateManager.getStateManager();
        builder = new MessageBuilder();
    }

    private JsonNode getStringAsJson(String string){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readTree(string);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void handleBaseMessage(String rawMessage){
        JsonNode json = getStringAsJson(rawMessage);
        if(json != null && json.has("type")){

            String type = getTrimmedStringFromJson(json, "type");
            switch (type){
                case "KeepAlive":
                    KeepAlive keepAlive = BaseMessage.parseKeepAlive(json);
                    if(stateManager.isNewMessage(keepAlive.getId())){
                        stateManager.handleKeepAlive(keepAlive);
                    }

                    break;
                case "Message":
                    Message message = BaseMessage.parseTextMessage(json);
                    if(stateManager.isNewMessage(message.getId())){
                        gui.addMessage(message);
                        stateManager.addMessage(message.getId());
                    }
                    break;
                case "Invite":
                    Invite invite = BaseMessage.parseInvite(json);
                    //notify gui
                    break;
            }

        }
    }

    static String getTrimmedStringFromJson(JsonNode json, String id){
        if(json.has(id)){
            return json.get(id).toString().replaceAll("\"", "");
        }
        throw new RuntimeException("can't find Field id: " + id + " in json");
    }

    public void sendKeepAlive(){
        try {
            ObjectNode keepAlive = builder.buildKeepAlive();
            addId(keepAlive);
            manager.sendMessage(keepAlive.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message){
        ChatRoom room = stateManager.getSelectedChatRoom();
        try{
            ObjectNode messageJson = builder.buildMessage(message, room, "Jan-Luca");
            addId(messageJson);
            manager.sendMessage(messageJson.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void addId(ObjectNode node){
        UUID id = UUID.randomUUID();
        node.put("id", id.toString());
        stateManager.addMessage(id);
    }
}
