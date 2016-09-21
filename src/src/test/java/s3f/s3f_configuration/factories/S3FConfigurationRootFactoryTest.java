package s3f.s3f_configuration.factories;

import org.junit.Test;
import s3f.s3f_configuration.dto.S3FConfigurationConstantDto;
import s3f.s3f_configuration.dto.S3FConfigurationDto;
import s3f.s3f_configuration.dto.S3FConfigurationRootDto;
import s3f.s3f_configuration.entities.S3FConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class S3FConfigurationRootFactoryTest {

    private final String lifecycle = "lifecycle";
    private final String version = "v1";

    @Test
    public void build() {
        S3FConfigurationRootFactory s3FConfigurationRootFactory = new S3FConfigurationRootFactory();

        S3FConfigurationRootDto s3FConfigurationRootDto = s3FConfigurationRootFactory.build(s3FConfigurationConstant(), s3FConfiguration());

        assertThat(s3FConfigurationRootDto, is(expected()));
    }

    private List<S3FConfigurationConstantDto> s3FConfigurationConstant() {
        List<S3FConfigurationConstantDto> configs = new ArrayList<>();
        configs.add(new S3FConfigurationConstantDto(version, lifecycle,"envConstantValue","ZKEuInxN6kZS6IR3IlOBfg=="));
        return configs;
    }

    private S3FConfiguration s3FConfiguration() {
        Map<String, String> keyValuePairs = new HashMap<>();
        keyValuePairs.put("serviceKey", "serviceValue");
        keyValuePairs.put("constantKey", "envConstantValue");
        return new S3FConfiguration("1", keyValuePairs, version, lifecycle, "ka-upload");
    }

    private S3FConfigurationRootDto expected(){
        Map<String, String> mergedKeyValuePairs = new HashMap<>();
        mergedKeyValuePairs.put("constantKey", "$encLogin");
        mergedKeyValuePairs.put("serviceKey", "serviceValue");
        return new S3FConfigurationRootDto(mergedKeyValuePairs, version, lifecycle, "ka-upload");
    }
}