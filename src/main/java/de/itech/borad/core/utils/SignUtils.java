package de.itech.borad.core.utils;

import java.security.*;

public class SignUtils {
    public static byte[] sign(byte[] data, PrivateKey privateKey) {
        try {
            Signature signer = Signature.getInstance("SHA256withRSA");
            signer.initSign(privateKey);
            signer.update(data);
            return (signer.sign());
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            return null;
        }
    }

    public static boolean verify(byte[] data, byte[] signature, PublicKey publicKey) {
        try {
            Signature signer = Signature.getInstance("SHA256withRSA");
            signer.initVerify(publicKey);
            signer.update(data);
            return (signer.verify(signature));
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            return false;
        }
    }
}