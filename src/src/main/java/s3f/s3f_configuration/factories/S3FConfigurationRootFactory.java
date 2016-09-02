package s3f.s3f_configuration.factories;

import org.springframework.stereotype.Component;
import s3f.s3f_configuration.entities.S3FConfiguration;
import s3f.s3f_configuration.entities.S3FConfigurationConstant;
import s3f.s3f_configuration.dto.S3FConfigurationRootDto;

import java.util.HashMap;
import java.util.Map;

@Component
public class S3FConfigurationRootFactory {

    public S3FConfigurationRootDto build(S3FConfigurationConstant s3FConfigurationConstant, S3FConfiguration s3FConfiguration) {
        Map<String, String> keyValuePairs = new HashMap<>();
        for (Map.Entry<String, String> keyAndValue : s3FConfiguration.getKeyValuePairs().entrySet()) {
            if (s3FConfigurationConstant.getKeyValuePairs().containsKey(keyAndValue.getValue())) {
                keyValuePairs.put(keyAndValue.getKey(), s3FConfigurationConstant.getKeyValuePairs().get(keyAndValue.getValue()));
            } else {
                keyValuePairs.put(keyAndValue.getKey(), keyAndValue.getValue());
            }
        }
        return new S3FConfigurationRootDto(keyValuePairs, s3FConfiguration.getVersion(), s3FConfiguration.getLifecycle(), s3FConfiguration.getService());
    }
}
