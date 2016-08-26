package s3f.s3f_configuration.dto;

import org.junit.Test;
import s3f.s3f_configuration.entities.S3FConfiguration;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class S3FConfigurationDtoTest {

    @Test
    public void equalS3FConfigurationDtos() throws Exception {
        final String version = "v1";
        final String lifecycle = "develop";
        final String service = "ka-upload";
        final HashMap<String, String> keyValuePairs = new HashMap<>();
        keyValuePairs.put("server.port", "30100");

        S3FConfigurationDto s3FConfigurationDto1 = new S3FConfigurationDto(keyValuePairs, version, lifecycle, service);
        S3FConfigurationDto s3FConfigurationDto2 = new S3FConfigurationDto(keyValuePairs, version, lifecycle, service);

        assertThat(s3FConfigurationDto1, is(s3FConfigurationDto2));
    }
}