package de.itech.borad.network;

import java.net.*;
import java.util.Enumeration;

public class UdpClient implements Runnable{

    private static final int SEND_PORT = 8888;

    private byte[] packet;

    private UdpManager manager;

    private DatagramSocket clientSocket;

    public UdpClient(UdpManager manager){
        this.manager = manager;
        try {
            clientSocket = new DatagramSocket();
            clientSocket.setBroadcast(true);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void setPacket(byte[] packet){
        this.packet = packet;
    }


    @Override
    public void run() {
        try {
            broadcastToAll();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    private void broadcastToAll() throws SocketException {
        Enumeration interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface networkInterface = (NetworkInterface) interfaces.nextElement();

            if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                continue; // Don't want to broadcast to the loopback interface
            }

            for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
                InetAddress broadcast = interfaceAddress.getBroadcast();
                if (broadcast == null) {
                    continue;
                }

                // Send the broadcast package!
                try {
                    DatagramPacket sendPacket = new DatagramPacket(packet, packet.length, broadcast, SEND_PORT);
                    clientSocket.send(sendPacket);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                System.out.println(getClass().getName() + ">>> Request packet sent to: " + broadcast.getHostAddress() + "; Interface: " + networkInterface.getDisplayName());
            }
        }
    }
}
