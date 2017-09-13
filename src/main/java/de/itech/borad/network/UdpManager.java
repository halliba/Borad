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

    public String getTestMessage(){
        return "{\"id\":\"f61f1f70-55a3-4991-b1d5-7cc0a669a182\",\"type\":\"Message\",\"chatRoom\":\"JBXrnxhKmHD04zVZH59pQHPeb0qkFf2WKGj2dCR/wo4=\",\"content\":\"jVbPHiifUFsKFO7FSr0z47y2DNL0iBZ01CFUMpq71DoMnI6E4Ikbh9WhG0l44rHic1dB9Gbj5sCpJ4DlAYh5kRgsVhApz7NT71UW6v2dGEH53q52O/F4uBPaGw/jjnVHTtTJRDLvPLSr5gAHwdw8mf8UGoIkxgmztJeRmSt0rzmb/8pJi7u6WDKsn8pwDtQHIKmanlPQel0vO25HEkn+ZyMBLwgKDLkEQRbq+7eQSs0UHfbR/YQdDwQ/8Qbf0HmPPBDpmCeUNc7gYJIrq8xKwGHeCV1nt+QHCyxWYKQb8ALWDBwgx/Ej6vzJbi0cY6EQRzkgkVoYZNd7kgkp8xc8sJ/KHZ3j15ikx3LxIFk7PL+4Kua9kpwSSvaFrXkMwevNRLii9sxst6VwfUqKj5CF7kdU/VqJbsBZhyFypg8ePTfiC0n0YJM42xjAQ3urhoE3J+l2R735U+ZwrVrb0axI6qk7Y1cOjFqeTqdBR8LE88QTOd01fDrQTs52Ce99murp/07BI+vIaJ2GGuODqRr1YBQY0QPBsp9hk53fE4AxE3qZZ0wmGDGnA0AuZsyHXbO7P4SkX90LZGsa9l3khM3Q4v1jUF+Byxko5DKuc7n2pZ2+agGhVwbFN5B4aMHJtZZ89Jt8jXlGyhatTuLV6WgTYE+CC/ih8icFx2TyV+pwNBqvqatuURzImqPOamyzA4Be5J9nxiYZz+ybe5o07MY+zRgIcfgYNzfjPTp+QY8iAmpgOyNty+S/a7QXU4KnhIonlIy3z0GFB3aBeIYPHTBk7R9rdVi43p7z37HudnvnMvj92+3oQo0q4OsodCQx5yRi90+P8Z4PpD7xwYMSrWeT2qijU0vO1fAWNcbjfOO4eLba0nsUgSVSmacHzQn/zYK9ya7F3DrOAiontbLDeuHfxFpcPTLbVwycCZ72AqHEgM29Rk681yVAAHttkTas07Etp3tZE1dAdWEI5zP2Hrw5QCI0KAVnoOHdcTh9sa0MdugsR2FD1OPzrnTZyCuvTABWPXNwAQcA0mJ6ks1jV+yzaQ==\",\"aesKey\":\"JBXrnxhKmHD04zVZH59pQHPeb0qkFf2WKGj2dCR/wo4=\"}";
    }

    public void sendMessage(String msg){
        msg = "FLEX" + msg;
        client.setPacket(msg.getBytes());
        new Thread(client).start();
    }

}
