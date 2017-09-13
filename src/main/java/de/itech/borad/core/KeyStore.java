package de.itech.borad.core;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;

public class KeyStore {

    private final static String FILE_PATH_NAME = "privateKey";

    private PrivateKey privateKey;
    private PublicKey publicKey;
    private KeyFactory keyFactory;

    public KeyStore() {
        File file = new File(FILE_PATH_NAME);
        if(file.exists()){
            try {
                keyFactory = KeyFactory.getInstance("RSA");
                loadFromFile();
                RSAPrivateCrtKey privk = (RSAPrivateCrtKey) privateKey;
                RSAPublicKeySpec publicKeySpec = new java.security.spec.RSAPublicKeySpec(privk.getModulus(), privk.getPublicExponent());
                publicKey = keyFactory.generatePublic(publicKeySpec);
            } catch (IOException | NullPointerException | NoSuchAlgorithmException | InvalidKeySpecException e) {
                generateNewKeyPair();
            }
        } else {
            generateNewKeyPair();
        }
    }

    private void generateNewKeyPair() {
        KeyPairGenerator generator = null;
        try {
            generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(1024);
            KeyPair pair = generator.generateKeyPair();
            privateKey = pair.getPrivate();
            publicKey = pair.getPublic();
            saveToFile();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("No key found and could not generate new ones\nnow exiting");
            System.exit(1);
        }
    }

    private void loadFromFile() throws InvalidKeySpecException, IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(FILE_PATH_NAME));
        privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encoded));
    }

    private void saveToFile() {
        byte[] key = privateKey.getEncoded();
        File file = new File(FILE_PATH_NAME);
        if(!file.exists()){
            try {
                FileOutputStream out = new FileOutputStream(FILE_PATH_NAME);
                out.write(key);
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public String getPublicKeyPem(){
        return PemUtils.getPemFromPublicKey(publicKey);
    }
}
