package de.itech.borad.core;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.itech.borad.client.Gui;
import de.itech.borad.models.Invite;
import de.itech.borad.models.KeepAlive;
import de.itech.borad.models.BaseMessage;
import de.itech.borad.models.Message;
import de.itech.borad.network.UdpManager;

import java.io.IOException;

public class MessageController {

    private Gui gui;

    private UdpManager manager;

    public void setGui(Gui gui){
        this.gui = gui;
    }

    public void setManager(UdpManager manager){
        this.manager = manager;
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
                    //notify keepAlive Manager (better name)
                    break;
                case "PublicMessage":
                    Message message = BaseMessage.parseTextMessage(json);
                    gui.addMessage(message);
                    //notify gui
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

    public void sendMessage(String message){
        manager.sendMessage(message);
    }
}
