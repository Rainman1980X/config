package s3f.s3f_configuration.dto;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.HashMap;

import org.junit.Test;

public class S3FConfigurationDtoTest {

    @Test
    public void equalS3FConfigurationDtos() throws Exception {
	final String id = "test_2908233490284ß902842390";
        final String version = "v1";
        final String lifecycle = "develop";
        final String service = "ka-upload";
        final HashMap<String, String> keyValuePairs = new HashMap<>();
        keyValuePairs.put("server.port", "30100");

	S3FConfigurationDto s3FConfigurationDto1 = new S3FConfigurationDto(id, keyValuePairs, version, lifecycle,
		service);
	S3FConfigurationDto s3FConfigurationDto2 = new S3FConfigurationDto(id, keyValuePairs, version, lifecycle,
		service);

        assertThat(s3FConfigurationDto1, is(s3FConfigurationDto2));
    }
}