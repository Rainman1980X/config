package s3f.s3f_configuration.services;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import s3f.s3f_configuration.dto.S3FConfigurationConstantDto;
import s3f.s3f_configuration.dto.S3FConfigurationDto;
import s3f.s3f_configuration.entities.S3FConfiguration;
import s3f.s3f_configuration.factories.S3FConfigurationRootFactory;
import s3f.s3f_configuration.repositories.S3FConfigurationRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class S3FConfigurationServiceTest {
    private S3FConfigurationRepository s3FConfigurationRepository;
    private S3FConfigurationService s3FConfigurationService;
    private S3FConfigurationRootFactory s3FConfigurationRootFactory;
    private final String service = "ka-upload";
    private final String version = "v1";
    private final String lifecycle = "production";

    @Before
    public void setUp() {
        s3FConfigurationRepository = mock(S3FConfigurationRepository.class);
        s3FConfigurationService = new S3FConfigurationService();
        s3FConfigurationRootFactory = mock(S3FConfigurationRootFactory.class);
        ReflectionTestUtils.setField(s3FConfigurationService, "s3FConfigurationRepository", s3FConfigurationRepository);
        ReflectionTestUtils.setField(s3FConfigurationService, "s3FConfigurationRootFactory", s3FConfigurationRootFactory);
    }

    @Test
    public void create() throws Exception{
        Map<String, String> keyValuePairs = new HashMap<>();
        keyValuePairs.put("server.port", "30100");

        S3FConfigurationDto s3FConfigurationDto = new S3FConfigurationDto(keyValuePairs, version, lifecycle, service);

        s3FConfigurationService.create(s3FConfigurationDto);

        verify(s3FConfigurationRepository).save(any(S3FConfiguration.class));
    }

    @Test
    public void read() throws Exception {
        List<S3FConfiguration> s3FConfigurations = new ArrayList<>();
        final HashMap<String, String> keyValuePairs = new HashMap<>();
        s3FConfigurations.add(new S3FConfiguration("1", keyValuePairs, version, lifecycle, service));

        when(s3FConfigurationRepository.findByServiceAndVersionAndLifecycle(service, version, lifecycle)).thenReturn(s3FConfigurations);
        s3FConfigurationService.read(service, version, lifecycle);

        verify(s3FConfigurationRepository).findByServiceAndVersionAndLifecycle(service, version, lifecycle);
    }

    @Test
    public void readAll() {
        final List<S3FConfiguration> s3FConfigurations = new ArrayList<>();
        Map<String, String> keyValuePairs = new HashMap<>();
        keyValuePairs.put("emergencyUNCPath", "\\\\san1.de\\emerg\\sample");

        s3FConfigurations.add(new S3FConfiguration("id", keyValuePairs, version, lifecycle, service));
        try {
            s3FConfigurationService.readAll(service, version, lifecycle);
        } catch (Exception e) {
            fail("Create method " + e.getMessage());
        }

        verify(s3FConfigurationRepository).findByServiceAndVersionAndLifecycle(service, version, lifecycle);
    }

    @Test
    public void build() {
        S3FConfiguration s3FConfiguration = new S3FConfiguration("1", new HashMap<>(), version, lifecycle, version);
        List<S3FConfigurationConstantDto> s3FConfigurationConstantDtos = new ArrayList<>();
        s3FConfigurationConstantDtos.add(new S3FConfigurationConstantDto(version, lifecycle,"",""));
        s3FConfigurationService.build(s3FConfigurationConstantDtos, s3FConfiguration);

        verify(s3FConfigurationRootFactory).build(s3FConfigurationConstantDtos, s3FConfiguration);
    }
}