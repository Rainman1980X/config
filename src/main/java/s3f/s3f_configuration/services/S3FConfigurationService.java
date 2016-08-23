package s3f.s3f_configuration.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import s3f.s3f_configuration.dto.S3FConfigurationDto;
import s3f.s3f_configuration.dto.S3FConfigurationRootDto;
import s3f.s3f_configuration.entities.S3FConfiguration;
import s3f.s3f_configuration.entities.S3FConfigurationConstant;
import s3f.s3f_configuration.factories.S3FConfigurationRootFactory;
import s3f.s3f_configuration.repositories.S3FConfigurationRepository;

import java.util.List;

@Service
public class S3FConfigurationService {
    @Autowired
    private S3FConfigurationRepository s3FConfigurationRepository;
    @Autowired
    private S3FConfigurationRootFactory s3FConfigurationRootFactory;

    public void create(S3FConfigurationDto s3FConfigurationDto) {
        S3FConfiguration s3FConfiguration = new S3FConfiguration(null, s3FConfigurationDto.getKeyValuePairs(), s3FConfigurationDto.getVersion(), s3FConfigurationDto.getLifecycle(), s3FConfigurationDto.getService());
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
        return s3FConfigurations.get(s3FConfigurations.size() - 1);
    }

    public S3FConfigurationRootDto build(S3FConfigurationConstant s3FConfigurationConstant, S3FConfiguration s3FConfiguration) {
        return s3FConfigurationRootFactory.build(s3FConfigurationConstant, s3FConfiguration);
    }
}
