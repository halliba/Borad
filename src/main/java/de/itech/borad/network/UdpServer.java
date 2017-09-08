package de.itech.borad.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.util.Arrays;

public class UdpServer implements Runnable{

    private DatagramSocket serverSocket;

    private UdpManager manager;

    private byte[] receiveData = new byte[15000];

    public UdpServer(UdpManager manager){
        this.manager = manager;
        try {
            serverSocket = new DatagramSocket(UdpManager.RECEIVE_PORT);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(true){
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            System.out.println("trying to receive Data!");
            try {
                serverSocket.receive(receivePacket);
            } catch (IOException e) {
                e.printStackTrace();
            }
            manager.receivedMessage(receivePacket.getData());
        }
    }
}
