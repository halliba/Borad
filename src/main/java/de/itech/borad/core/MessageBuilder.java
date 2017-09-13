package de.itech.borad.core;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Base64;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class MessageBuilder {

    private ObjectMapper mapper;
    private KeyStore keyStore;

    public MessageBuilder(){
        mapper = new ObjectMapper();
        keyStore = new KeyStore();
    }

    public JsonNode buildMessage(String text, byte[] chatRoom) throws IOException {
        ObjectNode node = mapper.createObjectNode();
        node.put("type", "PublicMessage");
        node.put("chatRoom", Base64.getEncoder().encode(chatRoom));
        ObjectNode content = mapper.createObjectNode();
        //content.put()
        return null;
    }

    public JsonNode buildKeepAlive(){
        ObjectNode node = mapper.createObjectNode();
        ObjectNode content = mapper.createObjectNode();
        ObjectNode data = mapper.createObjectNode();
        ObjectNode sender = mapper.createObjectNode();
        node.put("type", "KeepAlive");
        sender.put("name", "Jan-Luca");
        sender.put("publicKey", keyStore.getPublicKeyPem());
        data.set("sender", sender);
        try {
            String base64Data = Base64.getEncoder().encodeToString(data.toString().getBytes("UTF-8"));
            content.put("data", base64Data);
            byte[] sig = SignUtils.sign(base64Data.getBytes("UTF-8"), keyStore.getPrivateKey());
            content.put("signature", Base64.getEncoder().encodeToString(sig));
            String base64Content = Base64.getEncoder().encodeToString(content.toString().getBytes("UTF-8"));
            node.put("content", base64Content);
            return node;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
