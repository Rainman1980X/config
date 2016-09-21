package s3f.s3f_configuration.services;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class EncryptionDecryptionServiceTest {
    private EncryptionDecryptionService encryptionDecryptionService;

    @Before
    public void setup() {
        encryptionDecryptionService = new EncryptionDecryptionService();
    }

    @Test
    public void encrypt() throws Exception {
        String key = "@[toEncrypt]";
        String value = "derrohewert";
        String encrypted = encryptionDecryptionService.encrypt(value);

        assertThat(encrypted, not(value));
    }


    @Test
    public void encryptAndDecrypt() throws Exception {
        String key = "@[toEncrypt]";
        String value = "derrohewert";


        String encrypted = encryptionDecryptionService.encrypt(value);

        assertThat(encrypted, not(value));
        assertThat(encryptionDecryptionService.decrypt(encrypted), is(value));
    }

}