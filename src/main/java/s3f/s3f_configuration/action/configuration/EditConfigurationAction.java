package s3f.s3f_configuration.action.configuration;

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
public class EditConfigurationAction implements ConfigurationActions<S3FConfigurationDto> {

    @Override
    public ResponseEntity<HttpStatus> doActionOnConfiguration(S3FConfigurationRepository configurationRepository,
            MongoTemplate mongoTemplate, String authorization, String correlationToken,
            S3FConfigurationDto s3FConfigurationDto) {
        try {
            S3FConfigurationDto s3FConfigurationDtoUpdate = configurationRepository
                    .findById(s3FConfigurationDto.getId());
            if (s3FConfigurationDtoUpdate == null) {
                return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
            } else {
                S3FConfigurationDto s3FConfigurationUpdate = new S3FConfigurationDto(s3FConfigurationDto.getId(),
                        EncryptionDecryptionService.encryptKeyValueList(s3FConfigurationDto.getKeyValuePairs()),
                        s3FConfigurationDto.getVersion(), s3FConfigurationDto.getLifecycle(),
                        s3FConfigurationDto.getService());
                configurationRepository.save(s3FConfigurationUpdate);
                LoggerHelper.logData(Level.INFO, "Update configuration", correlationToken, authorization,
                        EditConfigurationAction.class.getName());
                return new ResponseEntity<HttpStatus>(HttpStatus.OK);
            }
        } catch (Exception e) {
            LoggerHelper.logData(Level.ERROR, "Edit configuration fails", correlationToken, authorization,
                    EditConfigurationAction.class.getName(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
