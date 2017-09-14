package de.itech.borad.core;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.itech.borad.client.chatlist.ChatRoom;
import de.itech.borad.core.utils.CryptUtils;
import de.itech.borad.core.utils.SignUtils;

import java.util.Base64;

import java.io.UnsupportedEncodingException;
import java.util.Date;

public class MessageBuilder {

    private ObjectMapper mapper;
    private KeyStore keyStore;

    public MessageBuilder() {
        mapper = new ObjectMapper();
        keyStore = KeyStore.getInstance();
    }

    public ObjectNode buildMessage(String text, ChatRoom chatRoom, String name) throws UnsupportedEncodingException {
        ObjectNode node = mapper.createObjectNode();
        ObjectNode content = mapper.createObjectNode();
        ObjectNode data = mapper.createObjectNode();
        ObjectNode sender = mapper.createObjectNode();

        byte[] roomIdentifier = chatRoom.getChatRoomIdentifier();

        sender.put("name", name);
        sender.put("publicKey", keyStore.getPublicKeyPem());
        data.set("sender", sender);
        data.put("content", text);
        data.put("timeStamp", (new Date().getTime() / 1000));
        String base64Data = Base64.getEncoder().encodeToString(data.toString().getBytes("UTF-8"));
        content.put("data", base64Data);
        byte[] sig = SignUtils.sign(base64Data.getBytes("UTF-8"), keyStore.getPrivateKey());
        content.put("signature", Base64.getEncoder().encodeToString(sig));
        node.put("type", "Message");
        node.put("chatRoom", Base64.getEncoder().encodeToString(roomIdentifier));
        byte[] encryptedContent = CryptUtils.aesEncryptByteArray(content.toString().getBytes("UTF-8"), chatRoom.getPreSharedKey());
        String base64Content = Base64.getEncoder().encodeToString(encryptedContent);
        node.put("content", base64Content);
        return node;
    }

    public ObjectNode buildKeepAlive() throws UnsupportedEncodingException {
        ObjectNode node = mapper.createObjectNode();
        ObjectNode content = mapper.createObjectNode();
        ObjectNode data = mapper.createObjectNode();
        ObjectNode sender = mapper.createObjectNode();
        node.put("type", "KeepAlive");
        sender.put("name", "Jan-Luca");
        sender.put("publicKey", keyStore.getPublicKeyPem());
        data.set("sender", sender);
        data.put("timeStamp", (new Date().getTime() / 1000));
        String base64Data = Base64.getEncoder().encodeToString(data.toString().getBytes("UTF-8"));
        content.put("data", base64Data);
        byte[] sig = SignUtils.sign(base64Data.getBytes("UTF-8"), keyStore.getPrivateKey());
        content.put("signature", Base64.getEncoder().encodeToString(sig));
        String base64Content = Base64.getEncoder().encodeToString(content.toString().getBytes("UTF-8"));
        node.put("content", base64Content);
        return node;
    }
}
