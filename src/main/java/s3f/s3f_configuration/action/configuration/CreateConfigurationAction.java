package s3f.s3f_configuration.action.configuration;

import java.util.List;

import org.apache.log4j.Level;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import s3f.framework.logger.LoggerHelper;
import s3f.framework.encryption.EncryptionDecryptionService;
import s3f.s3f_configuration.dto.S3FConfigurationDto;
import s3f.s3f_configuration.repositories.S3FConfigurationRepository;

@Service
public class CreateConfigurationAction implements ConfigurationActions<S3FConfigurationDto> {

    private S3FConfigurationRepository configurationRepository;

    @Override
    public ResponseEntity<S3FConfigurationDto> doActionOnConfiguration(
            S3FConfigurationRepository configurationRepository, MongoTemplate mongoTemplate, String authorization,
            String correlationToken, S3FConfigurationDto s3FConfigurationDto) {
        this.configurationRepository = configurationRepository;

        try {
            if (hasDoubleEntry(s3FConfigurationDto)) {
                LoggerHelper.logData(Level.WARN, "Duplicate entry configuration", correlationToken, authorization,
                        CreateConfigurationAction.class.getName());
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            } else {
                S3FConfigurationDto s3FConfiguration = new S3FConfigurationDto(null,
                        EncryptionDecryptionService.encryptKeyValueList(s3FConfigurationDto.getKeyValuePairs()),
                        s3FConfigurationDto.getVersion(), s3FConfigurationDto.getLifecycle(),
                        s3FConfigurationDto.getService());
                configurationRepository.insert(s3FConfiguration);
                LoggerHelper.logData(Level.INFO, "Create configuration", correlationToken, authorization,
                        CreateConfigurationAction.class.getName());

                return new ResponseEntity<>(HttpStatus.OK);
            }

        } catch (Exception e) {
            LoggerHelper.logData(Level.ERROR, "Create configuration fails", correlationToken, authorization,
                    CreateConfigurationAction.class.getName(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean hasDoubleEntry(S3FConfigurationDto s3FConfigurationDto) {
        final List<S3FConfigurationDto> configurationDtos = configurationRepository.findByServiceAndVersionAndLifecycle(
                s3FConfigurationDto.getService(), s3FConfigurationDto.getVersion(), s3FConfigurationDto.getLifecycle());
        return configurationDtos.size() > 0;
    }

}
