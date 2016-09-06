package s3f.s3f_configuration.services;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class EncryptionDecryptionServiceTest {
    private EncryptionDecryptionService encryptionDecryptionService;

    @Before
    public void setup(){
        encryptionDecryptionService = new EncryptionDecryptionService();
    }
    @Test
    public void encrypt() throws Exception {
        String key = "@[toEncrypt]";
        String value = "derrohewert";
        Map<String, String> keyValuePairs = new HashMap<>();
        keyValuePairs.put(key, value);

        Map<String, String> encrypted = encryptionDecryptionService.encrypt(keyValuePairs);

        assertThat(encrypted, not(keyValuePairs));
    }

    @Test
    public void encryptNothing() throws Exception {
        String key = "notEncrypt";
        String value = "derrohewert";
        Map<String, String> keyValuePairs = new HashMap<>();
        keyValuePairs.put(key, value);

        Map<String, String> encrypted = encryptionDecryptionService.encrypt(keyValuePairs);

        assertThat(encrypted, is(keyValuePairs));
    }

    @Test
    public void encryptAndDecrypt() throws Exception {
        String key = "@[toEncrypt]";
        String value = "derrohewert";
        Map<String, String> keyValuePairs = new HashMap<>();
        keyValuePairs.put(key, value);

        Map<String, String> encrypted = encryptionDecryptionService.encrypt(keyValuePairs);

        assertThat(encrypted, not(keyValuePairs));
        assertThat(encryptionDecryptionService.decrypt(encrypted), is(keyValuePairs));
    }


    @Test
    public void decryptNothing() throws Exception {
        String key = "notEncrypt";
        String value = "derrohewert";
        Map<String, String> keyValuePairs = new HashMap<>();
        keyValuePairs.put(key, value);

        Map<String, String> unencrypted  = encryptionDecryptionService.decrypt(keyValuePairs);

        assertThat(unencrypted, is(keyValuePairs));
    }
}