package de.itech.borad.core;

import de.itech.borad.client.Gui;
import de.itech.borad.models.ChatMessage;
import de.itech.borad.network.UdpManager;

public class MessageController {

    private Gui gui;

    private UdpManager manager;

    public void setGui(Gui gui){
        this.gui = gui;
    }

    public void setManager(UdpManager manager){
        this.manager = manager;
    }

    public void handleMessage(String message){
        gui.addMessage(new ChatMessage("test", message));
    }

    public void sendMessage(String message){
        manager.sendMessage(message);
    }
}
