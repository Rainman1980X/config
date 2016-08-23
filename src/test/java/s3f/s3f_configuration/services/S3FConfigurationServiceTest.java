package s3f.s3f_configuration.services;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import s3f.s3f_configuration.dto.S3FConfigurationDto;
import s3f.s3f_configuration.entities.S3FConfiguration;
import s3f.s3f_configuration.repositories.S3FConfigurationRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class S3FConfigurationServiceTest {
    private S3FConfigurationRepository s3FConfigurationRepository;
    private S3FConfigurationService s3FConfigurationService;

    @Before
    public void setUp() {
        s3FConfigurationRepository = mock(S3FConfigurationRepository.class);
        s3FConfigurationService = new S3FConfigurationService();
        ReflectionTestUtils.setField(s3FConfigurationService, "s3FConfigurationRepository", s3FConfigurationRepository);
    }

    @Test
    public void create() {
        Map<String, String> keyValuePairs = new HashMap<>();
        keyValuePairs.put("key", "value");

        S3FConfigurationDto s3FConfigurationDto = new S3FConfigurationDto(keyValuePairs, "version", "lifecycle", "service");

        s3FConfigurationService.create(s3FConfigurationDto);

        verify(s3FConfigurationRepository).save(any(S3FConfiguration.class));
    }

    @Test
    public void readAll() {
        final List<S3FConfiguration> s3FConfigurations = new ArrayList<>();
        final String service = "ka-upload";
        final String version = "v1";
        final String lifecycle = "production";
        Map<String, String> keyValuePairs = new HashMap<>();
        keyValuePairs.put("emergencyUNCPath", "\\\\san1.de\\emerg\\sample");

        s3FConfigurations.add(new S3FConfiguration("id", keyValuePairs, version, lifecycle, service));
        s3FConfigurationService.readAll(service, version, lifecycle);

        verify(s3FConfigurationRepository).findByServiceAndVersionAndLifecycle(service, version, lifecycle);
    }
}