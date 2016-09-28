package s3f.s3f_configuration.factories;

import org.springframework.stereotype.Component;

import s3f.framework.security.EncryptionDecryptionService;
import s3f.s3f_configuration.dto.S3FConfigurationConstantDto;
import s3f.s3f_configuration.dto.S3FConfigurationRootDto;
import s3f.s3f_configuration.entities.S3FConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class S3FConfigurationRootFactory {

    public S3FConfigurationRootDto build(List<S3FConfigurationConstantDto> s3FConfigurationConstantDtos, S3FConfiguration s3FConfiguration) {
        Map<String, String> s3FConstantsMap = new HashMap<>();
        try {
            s3FConstantsMap = s3FConfigurationConstantDtos.stream()
                    .collect(Collectors.toMap(
                            s3FConfigurationConstantDto -> s3FConfigurationConstantDto.getConstantName(),
                            s3FConfigurationConstantDto -> s3FConfigurationConstantDto.getConstantValue()));
        } catch (Exception e) {
            throw e;
        }


        Map<String, String> mergedKeyValuePairs = new HashMap<>();

        for (Map.Entry<String, String> keyAndValue : s3FConfiguration.getKeyValuePairs().entrySet()) {
            if (s3FConstantsMap.containsKey(keyAndValue.getValue())) {
                mergedKeyValuePairs.put(keyAndValue.getKey(), s3FConstantsMap.get(keyAndValue.getValue()));
            } else {
                mergedKeyValuePairs.put(keyAndValue.getKey(), keyAndValue.getValue());
            }
        }
        return new S3FConfigurationRootDto(mergedKeyValuePairs, s3FConfiguration.getVersion(), s3FConfiguration.getLifecycle(), s3FConfiguration.getService());
    }

    public List<S3FConfigurationRootDto> build(List<S3FConfigurationConstantDto> s3FConfigurationConstantDtos, List<S3FConfiguration> s3FConfigurations) {
        List<S3FConfigurationRootDto> s3FConfigurationRootDtos = new ArrayList<>();
        Map<String, String> s3FConstantsMap = new HashMap<>();
        try {
            s3FConstantsMap = s3FConfigurationConstantDtos.stream()
                    .collect(Collectors.toMap(
                            s3FConfigurationConstantDto -> s3FConfigurationConstantDto.getConstantName(),
                            s3FConfigurationConstantDto -> s3FConfigurationConstantDto.getConstantValue()));
        } catch (Exception e) {
            throw e;
        }

        Map<String, String> mergedKeyValuePairs = new HashMap<>();
        for (S3FConfiguration s3FConfiguration : s3FConfigurations) {
            for (Map.Entry<String, String> keyAndValue : s3FConfiguration.getKeyValuePairs().entrySet()) {
                if (s3FConstantsMap.containsKey(keyAndValue.getValue())) {
                    mergedKeyValuePairs.put(keyAndValue.getKey(), s3FConstantsMap.get(keyAndValue.getValue()));
                } else {
                    mergedKeyValuePairs.put(keyAndValue.getKey(), keyAndValue.getValue());
                }
            }
            s3FConfigurationRootDtos.add(new S3FConfigurationRootDto(mergedKeyValuePairs, s3FConfiguration.getVersion(), s3FConfiguration.getLifecycle(), s3FConfiguration.getService()));
        }

        return s3FConfigurationRootDtos;
    }
}
