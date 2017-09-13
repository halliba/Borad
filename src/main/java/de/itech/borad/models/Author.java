package de.itech.borad.models;

import java.security.PublicKey;

public class Author {

    private String name;
    private PublicKey publicKey;

    public Author(String name, PublicKey publicKey){
        this.name = name;
        this.publicKey = publicKey;
    }

    public String getName() {
        return name;
    }

    public PublicKey getPublicKey(){
        return publicKey;
    }
}
