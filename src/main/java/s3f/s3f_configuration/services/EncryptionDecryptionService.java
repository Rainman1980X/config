package s3f.s3f_configuration.services;

import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.AlgorithmParameters;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@Service
public class EncryptionDecryptionService {
    private byte[] ivBytes;

    private static SecretKey secretKey;

    public Map<String, String> encrypt(Map<String, String> source) throws Exception {
        Map<String, String> result = new HashMap<>(source.size());
        for (Map.Entry<String, String> keyAndValue : source.entrySet()) {
            final String key = keyAndValue.getKey();
            final String value = keyAndValue.getValue();
            if (isMarkedAsEncrypted(key)) {
                result.put(key, encrypt(value.toCharArray()));
            } else {
                result.put(key, value);
            }
        }
        return result;
    }

    public Map<String, String> decrypt(Map<String, String> source) throws Exception {
        Map<String, String> result = new HashMap<>(source.size());
        for (Map.Entry<String, String> keyAndValue : source.entrySet()) {
            final String key = keyAndValue.getKey();
            final String value = keyAndValue.getValue();
            if (isMarkedAsEncrypted(key)) {
                result.put(key, decrypt(value.toCharArray()));
            } else {
                result.put(key, value);
            }
        }
        return result;
    }

    private boolean isMarkedAsEncrypted(String key) {
        boolean result = false;
        if (key.startsWith("@[") && key.endsWith("]")) {
            result = true;
        }
        return result;
    }

    public String encrypt(char[] plaintext) throws Exception {
        byte[] saltBytes = getSalt().getBytes();

        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        int iterations = 65536;
        int keySize = 128;
        PBEKeySpec spec = new PBEKeySpec(plaintext, saltBytes, iterations, keySize);
        secretKey = skf.generateSecret(spec);
        SecretKeySpec secretSpec = new SecretKeySpec(secretKey.getEncoded(), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretSpec);
        AlgorithmParameters params = cipher.getParameters();
        ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();
        byte[] encryptedTextBytes = cipher.doFinal(String.valueOf(plaintext).getBytes("UTF-8"));

        return DatatypeConverter.printBase64Binary(encryptedTextBytes);
    }

    public String decrypt(char[] encryptedText) throws Exception {
        byte[] encryptedTextBytes = DatatypeConverter.parseBase64Binary(new String(encryptedText));
        SecretKeySpec secretSpec = new SecretKeySpec(secretKey.getEncoded(), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretSpec, new IvParameterSpec(ivBytes));

        return new String(cipher.doFinal(encryptedTextBytes));

    }

    private String getSalt() throws Exception {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[20];
        sr.nextBytes(salt);
        return new String(salt);
    }
}
