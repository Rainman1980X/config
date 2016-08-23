package s3f.s3f_configuration.services;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class EncryptionDecryptionServiceTest {
    @Test
    public void encrypt() throws Exception {
        EncryptionDecryptionService encryptionDecryptionService = new EncryptionDecryptionService();
        String value = "derrohewert";

        assertThat(encryptionDecryptionService.encrypt(value.toCharArray()), not(nullValue()));
    }

    @Test
    public void encryptAndDecrypt() throws Exception {
        EncryptionDecryptionService encryptionDecryptionService = new EncryptionDecryptionService();
        String value = "derrohewert";

        final String encryptedValue = encryptionDecryptionService.encrypt(value.toCharArray());
        final String decryptetValue = encryptionDecryptionService.decrypt(encryptedValue.toCharArray());
        assertThat(decryptetValue, is(value));
    }

}