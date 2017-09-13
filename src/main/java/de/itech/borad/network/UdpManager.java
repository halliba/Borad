package de.itech.borad.network;

import de.itech.borad.core.MessageController;

import java.nio.charset.Charset;

public class UdpManager {

    public static final int RECEIVE_PORT = 34567;

    private UdpClient client;

    private MessageController controller;

    public UdpManager(){
        client = new UdpClient(this);
        controller = new MessageController();
    }

    public void setMessageController(MessageController controller){
        this.controller = controller;
    }

    public void startListening(){
        new Thread(new UdpServer(this)).start();
    }

    public void receivedMessage(byte[] msg){
        String s = new String(msg, Charset.forName("UTF-8")).trim();
        if(s.startsWith("FLEX")){
            controller.handleBaseMessage(s.substring(4));
        }
    }

    public void sendMessage(String msg){
        msg = "FLEX" + msg;
        client.setPacket(msg.getBytes());
        new Thread(client).start();
    }

}
