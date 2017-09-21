package de.itech.borad.models;

import java.security.PublicKey;
import java.util.Date;

public class User {

    private static final int ONLINE_TIMEOUT = 10;

    private String name;
    private PublicKey publicKey;
    private Date lastMessageReceived;

    public User(String name, PublicKey publicKey){
        this.name = name;
        this.publicKey = publicKey;
    }

    public String getName() {
        return name;
    }

    public PublicKey getPublicKey(){
        return publicKey;
    }

    public void receivedMessage(){
        lastMessageReceived = new Date();
    }

    public boolean isSameUser(User user){
        return this.publicKey.equals(user.publicKey);
    }

    public boolean isOnline(){
        long timeSinceMessage = new Date().getTime() - lastMessageReceived.getTime();
        return timeSinceMessage < ONLINE_TIMEOUT * 1000;
    }
}
