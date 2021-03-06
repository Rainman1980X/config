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

public class S3FConfigurationRootDtoFactoryTest {
    private final String id = "test_2908233490284ß902842390";
    private final String version = "v1";
    private final String lifecycle = "production";
    private final String serviceName = "ka-upload";

    @Test
    public void build() {
	final Map<String, String> keyValuePairsOfS3FConfiguration = new HashMap<>();
	keyValuePairsOfS3FConfiguration.put("waitOnRetry", "4");
	keyValuePairsOfS3FConfiguration.put("mongoPassword", "@[mongoPassword]");
	S3FConfigurationDto s3FConfiguration = new S3FConfigurationDto("1", keyValuePairsOfS3FConfiguration, version,
		lifecycle, serviceName);
	List<S3FConfigurationConstantDto> s3FConfigurationConstantDtos = new ArrayList<>();
	s3FConfigurationConstantDtos.add(new S3FConfigurationConstantDto(id, version, lifecycle, "@[mongoPassword]",
		"ZKEuInxN6kZS6IR3IlOBfg=="));
	S3FConfigurationDto s3FConfigurationRootDto = new S3FConfigurationRootBuilder()
		.build(s3FConfigurationConstantDtos, s3FConfiguration);

	//assertThat(s3FConfigurationRootDto, is(expected()));
    }

    private S3FConfigurationDto expected() {
	final Map<String, String> keyValuePairs = new HashMap<>();
	keyValuePairs.put("waitOnRetry", "4");
	keyValuePairs.put("mongoPassword", "$encLogin");

	return new S3FConfigurationDto(id, keyValuePairs, version, lifecycle, serviceName);
    }
}