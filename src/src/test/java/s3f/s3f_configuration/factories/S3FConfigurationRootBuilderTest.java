package s3f.s3f_configuration.factories;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import s3f.s3f_configuration.dto.S3FConfigurationConstantDto;
import s3f.s3f_configuration.dto.S3FConfigurationDto;

public class S3FConfigurationRootBuilderTest {

    private final String id = "test_2908233490284ß902842390";
    private final String lifecycle = "lifecycle";
    private final String version = "v1";

    @Test
    public void build() {
        S3FConfigurationRootBuilder s3FConfigurationRootFactory = new S3FConfigurationRootBuilder();

	S3FConfigurationDto s3FConfigurationRootDto = s3FConfigurationRootFactory.build(s3FConfigurationConstant(),
		s3FConfiguration());

        //assertThat(s3FConfigurationRootDto, is(expected()));
    }

    private List<S3FConfigurationConstantDto> s3FConfigurationConstant() {
        List<S3FConfigurationConstantDto> configs = new ArrayList<>();
	configs.add(new S3FConfigurationConstantDto(id, version, lifecycle, "envConstantValue",
		"ZKEuInxN6kZS6IR3IlOBfg=="));
        return configs;
    }

    private S3FConfigurationDto s3FConfiguration() {
        Map<String, String> keyValuePairs = new HashMap<>();
        keyValuePairs.put("serviceKey", "serviceValue");
        keyValuePairs.put("constantKey", "envConstantValue");
	return new S3FConfigurationDto(id, keyValuePairs, version, lifecycle, "ka-upload");
    }

    private S3FConfigurationDto expected() {
        Map<String, String> mergedKeyValuePairs = new HashMap<>();
        mergedKeyValuePairs.put("constantKey", "$encLogin");
        mergedKeyValuePairs.put("serviceKey", "serviceValue");
	return new S3FConfigurationDto(id, mergedKeyValuePairs, version, lifecycle, "ka-upload");
    }
}