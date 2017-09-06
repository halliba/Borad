package de.itech.borad.core;

import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.bouncycastle.util.io.pem.PemWriter;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

class PemUtils {
    public static PrivateKey getPrivateKeyFromPem(String pemContent) {
        try {
            StringReader reader = new StringReader(pemContent);
            byte[] content = new PemReader(reader).readPemObject().getContent();
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(content);
            return KeyFactory.getInstance("RSA", "BC").generatePrivate(privateKeySpec);
        } catch (NoSuchAlgorithmException
                | IOException
                | InvalidKeySpecException
                | NoSuchProviderException e) {
            return null;
        }
    }

    public static PublicKey getPublicKeyFromPem(String pemContent) {
        try {
            StringReader reader = new StringReader(pemContent);
            byte[] content = new PemReader(reader).readPemObject().getContent();
            PKCS8EncodedKeySpec publicKeySpec = new PKCS8EncodedKeySpec(content);
            return KeyFactory.getInstance("RSA", "BC").generatePublic(publicKeySpec);
        } catch (NoSuchAlgorithmException
                | IOException
                | InvalidKeySpecException
                | NoSuchProviderException e) {
            return null;
        }
    }

    public static String getPemFromPublicKey(PublicKey key)
    {
        PemObject pemObject = new PemObject("RSA PUBLIC KEY", key.getEncoded());
        StringWriter writer = new StringWriter();
        PemWriter pemWriter = new PemWriter(writer);
        try {
            pemWriter.writeObject(pemObject);
        } catch (IOException e) {
            return null;
        }
        return writer.toString();
    }

    public static String getPemFromPrivateKey(PrivateKey key)
    {
        PemObject pemObject = new PemObject("RSA PRIVATE KEY", key.getEncoded());
        StringWriter writer = new StringWriter();
        PemWriter pemWriter = new PemWriter(writer);
        try {
            pemWriter.writeObject(pemObject);
        } catch (IOException e) {
            return null;
        }
        return writer.toString();
    }
}