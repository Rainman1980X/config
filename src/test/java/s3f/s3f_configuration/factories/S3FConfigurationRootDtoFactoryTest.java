package s3f.s3f_configuration.factories;

import org.junit.Test;
import s3f.s3f_configuration.entities.S3FConfiguration;
import s3f.s3f_configuration.entities.S3FConfigurationConstant;
import s3f.s3f_configuration.dto.S3FConfigurationRootDto;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class S3FConfigurationRootDtoFactoryTest {
    private final String version = "v1";
    private final String lifecycle = "production";
    private final String serviceName = "ka-upload";

    @Test
    public void build() {
        final Map<String, String> keyValuePairsOfS3FConfiguration = new HashMap<>();
        keyValuePairsOfS3FConfiguration.put("waitOnRetry", "4");
        keyValuePairsOfS3FConfiguration.put("mongoPassword", "@[mongoPassword]");
        S3FConfiguration s3FConfiguration = new S3FConfiguration("1", keyValuePairsOfS3FConfiguration, version, lifecycle, serviceName);
        final Map<String, String> keyValuePairsOfS3FConfigurationConstant = new HashMap<>();
        keyValuePairsOfS3FConfigurationConstant.put("@[mongoPassword]", "topsecret");
        S3FConfigurationConstant s3FConfigurationConstant = new S3FConfigurationConstant("111", keyValuePairsOfS3FConfigurationConstant, version, lifecycle);

        S3FConfigurationRootDto s3FConfigurationRootDto = new S3FConfigurationRootFactory().build(s3FConfigurationConstant, s3FConfiguration);

        assertThat(s3FConfigurationRootDto, is(expected()));
    }

    private S3FConfigurationRootDto expected() {
        final Map<String, String> keyValuePairs = new HashMap<>();
        keyValuePairs.put("waitOnRetry", "4");
        keyValuePairs.put("mongoPassword", "topsecret");

        return new S3FConfigurationRootDto(keyValuePairs, version, lifecycle, serviceName);
    }
}