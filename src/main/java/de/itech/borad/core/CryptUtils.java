package de.itech.borad.core;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.BlockCipherPadding;
import org.bouncycastle.crypto.paddings.PKCS7Padding;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;

public class CryptUtils {
    public static byte[] genrateAesKey() {
        SecureRandom random = new SecureRandom();
        byte[] keyBytes = new byte[32];
        random.nextBytes(keyBytes);
        return keyBytes;
    }

    public static byte[] rsaEncryptWithPublic(byte[] data, AsymmetricKeyParameter publicKey) {
        try {
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher;
            byte[] encrypted;
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, (Key) publicKey);
            encrypted = cipher.doFinal(data);
            return encrypted;
        } catch (NoSuchAlgorithmException
                | InvalidKeyException
                | NoSuchPaddingException
                | IllegalBlockSizeException
                | BadPaddingException e) {
            return null;
        }
    }

    public static byte[] rsaDecryptWithPrivate(byte[] data, AsymmetricKeyParameter privateKey) {
        try {
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher;
            byte[] decrypted = null;
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, (Key) privateKey);
            decrypted = cipher.doFinal(data);
            return decrypted;
        } catch (NoSuchAlgorithmException
                | InvalidKeyException
                | NoSuchPaddingException
                | IllegalBlockSizeException
                | BadPaddingException e) {
            return null;
        }
    }

    /**
     * Encrypt a byte array using AES
     * @param secret byte array that need to be encrypted
     * @param key 256 bit key
     * @return Encrypted array
     */
    public static byte[] aesEncryptByteArray(byte[] secret, byte[] key) {
        KeyParameter keyParameter = new KeyParameter(key);

        BlockCipher AESCipher = new AESEngine();
        PaddedBufferedBlockCipher pbbc = new PaddedBufferedBlockCipher(AESCipher, new PKCS7Padding());
        pbbc.init(true, keyParameter);

        byte[] output = new byte[pbbc.getOutputSize(secret.length)];
        int bytesWrittenOut = pbbc.processBytes(
                secret, 0, secret.length, output, 0);

        try {
            pbbc.doFinal(output, bytesWrittenOut);
        } catch (InvalidCipherTextException e) {
            return null;
        }

        return output;
    }

    /**
     * Decrypt a byte array using AES
     * @param encrypted 256 bit key
     * @param key
     * @return decrypted bytes
     */
    public static byte[] aesDecryptByteArray(byte[] encrypted, byte[] key) {
        byte[] iv = new byte[16]; //initial vector is 16 bytes
        for(int i = 0; i < 16; i++){
            iv[i] = encrypted[i];
        }
        byte[] encryptedContent = new byte[encrypted.length - 16];
        for(int i = 16; i < encrypted.length; i++){
            encryptedContent[i-16] = encrypted[i];
        }

        KeyParameter keyParam = new KeyParameter(key);
        CipherParameters params = new ParametersWithIV(keyParam, iv);

        BlockCipherPadding padding = new PKCS7Padding();
        BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(
                new CBCBlockCipher(new AESEngine()), padding);
        cipher.reset();
        cipher.init(false, params);

        byte[] buf = new byte[cipher.getOutputSize(encryptedContent.length)];
        int len = cipher.processBytes(encryptedContent, 0, encryptedContent.length, buf, 0);
        try {
            len += cipher.doFinal(buf, len);
        } catch (InvalidCipherTextException e) {
            return null;
        }

        byte[] out = new byte[len];
        System.arraycopy(buf, 0, out, 0, len);

        return out;
    }
}