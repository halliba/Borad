package de.itech.borad.network;

import java.nio.charset.Charset;

public class UdpManager {

    UdpClient client;

    public UdpManager(){
        Thread serverThread = new Thread(new UdpServer(this));
        client = new UdpClient(this);
        serverThread.start();
    }

    public void receivedMessage(byte[] msg){
        String s = new String(msg, Charset.forName("UTF-8")).trim();
        System.out.println(s);
    }

    public void sendMessage(String msg){
        client.setPacket(msg.getBytes());
        new Thread(client).start();
    }

}
