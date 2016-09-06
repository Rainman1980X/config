package s3f.framework.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import s3f.Application;

import java.util.HashMap;
import java.util.Map;

public class S3FPlaceholderConfigurer extends PropertySourcesPlaceholderConfigurer {
    private static final Logger LOGGER = LoggerFactory.getLogger(S3FPlaceholderConfigurer.class);

    public S3FPlaceholderConfigurer() {
        if (Application.useConfigService) {
            setIgnoreUnresolvablePlaceholders(true);
            MutablePropertySources mutablePropertySources = new MutablePropertySources();
            mutablePropertySources.addFirst(new MapPropertySource("custom", loadProperties()));
            setPropertySources(mutablePropertySources);
        } else {
            LOGGER.info("Not calling ConfigService for Service Properties");
        }

    }

    private Map<String, Object> loadProperties() {
        Map<String, Object> keyValuePairs = new HashMap<>();
        RestTemplate restTemplate = new RestTemplate();
        final String url = "http://localhost:30001/api/v1/s3f-configuration/" + Application.serviceName + "/v1/" + Application.lifecycle;
        LOGGER.info(url);
        try {
            S3FConfigurationDto s3FConfigurationDto = restTemplate.getForObject(url, S3FConfigurationDto.class);
            LOGGER.info(s3FConfigurationDto.toString());
            keyValuePairs.putAll(s3FConfigurationDto.getKeyValuePairs());
        } catch (Exception e){
            LOGGER.warn("S3F-Config not running. application.properties fallback.");
        }
        return keyValuePairs;
    }
}
