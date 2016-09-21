package s3f.s3f_configuration.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import s3f.s3f_configuration.dto.S3FConfigurationConstantDto;
import s3f.s3f_configuration.dto.S3FConfigurationDto;
import s3f.s3f_configuration.dto.S3FConfigurationRootDto;
import s3f.s3f_configuration.entities.S3FConfiguration;
import s3f.s3f_configuration.factories.S3FConfigurationRootFactory;
import s3f.s3f_configuration.repositories.S3FConfigurationRepository;

import java.util.List;

@Service
public class S3FConfigurationService {
    @Autowired
    private S3FConfigurationRepository s3FConfigurationRepository;
    @Autowired
    private S3FConfigurationRootFactory s3FConfigurationRootFactory;
    @Autowired
    private EscapeService escapeService;

    public void create(S3FConfigurationDto s3FConfigurationDto) {
        S3FConfiguration s3FConfiguration = new S3FConfiguration(null, escapeService.escape(s3FConfigurationDto.getKeyValuePairs()), s3FConfigurationDto.getVersion(), s3FConfigurationDto.getLifecycle(), s3FConfigurationDto.getService());
        s3FConfigurationRepository.save(s3FConfiguration);
    }

    public List<S3FConfiguration> readAll(String service, String version, String lifecycle) {
        return s3FConfigurationRepository.findByServiceAndVersionAndLifecycle(service, version, lifecycle);
    }

    public S3FConfiguration read(String service, String version, String lifecycle) throws Exception {
        final List<S3FConfiguration> s3FConfigurations = s3FConfigurationRepository.findByServiceAndVersionAndLifecycle(service, version, lifecycle);
        if (s3FConfigurations.size() == 0) {
            throw new Exception("No S3FConfiguration in DB");
        }
        final S3FConfiguration s3FConfiguration = s3FConfigurations.get(s3FConfigurations.size() - 1);
        final S3FConfiguration unescaped = new S3FConfiguration(
                s3FConfiguration.getId(),
                escapeService.unescape(s3FConfiguration.getKeyValuePairs()),
                s3FConfiguration.getVersion(),
                s3FConfiguration.getLifecycle(),
                s3FConfiguration.getService()
        );
        return unescaped;
    }

    public S3FConfigurationRootDto build(List<S3FConfigurationConstantDto> s3FConfigurationConstants, S3FConfiguration s3FConfiguration) {
        return s3FConfigurationRootFactory.build(s3FConfigurationConstants, s3FConfiguration);
    }
}
