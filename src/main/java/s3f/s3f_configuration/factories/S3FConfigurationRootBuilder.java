package s3f.s3f_configuration.factories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import s3f.s3f_configuration.dto.S3FConfigurationConstantDto;
import s3f.s3f_configuration.dto.S3FConfigurationDto;

@Component
public class S3FConfigurationRootBuilder {

    public static S3FConfigurationDto build(List<S3FConfigurationConstantDto> s3FConfigurationConstantDtos,
            S3FConfigurationDto s3FConfiguration) {
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
        return new S3FConfigurationDto(s3FConfiguration.getId(), mergedKeyValuePairs, s3FConfiguration.getVersion(),
                s3FConfiguration.getLifecycle(), s3FConfiguration.getService());
    }

    public static List<S3FConfigurationDto> build(List<S3FConfigurationConstantDto> s3FConfigurationConstantDtos,
            List<S3FConfigurationDto> s3FConfigurations) {
        List<S3FConfigurationDto> s3FConfigurationRootDtos = new ArrayList<>();
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
        for (S3FConfigurationDto s3FConfiguration : s3FConfigurations) {
            for (Map.Entry<String, String> keyAndValue : s3FConfiguration.getKeyValuePairs().entrySet()) {
                if (s3FConstantsMap.containsKey(keyAndValue.getValue())) {
                    mergedKeyValuePairs.put(keyAndValue.getKey(), s3FConstantsMap.get(keyAndValue.getValue()));
                } else {
                    mergedKeyValuePairs.put(keyAndValue.getKey(), keyAndValue.getValue());
                }
            }
            s3FConfigurationRootDtos.add(new S3FConfigurationDto(s3FConfiguration.getId(), mergedKeyValuePairs,
                    s3FConfiguration.getVersion(), s3FConfiguration.getLifecycle(), s3FConfiguration.getService()));
        }

        return s3FConfigurationRootDtos;
    }
}
