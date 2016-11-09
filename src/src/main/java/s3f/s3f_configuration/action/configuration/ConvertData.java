package s3f.s3f_configuration.action.configuration;

import java.util.List;

import org.apache.log4j.Level;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import s3f.framework.logger.LoggerHelper;
import s3f.framework.encryption.EncryptionDecryptionService;
import s3f.s3f_configuration.dto.S3FConfigurationDto;
import s3f.s3f_configuration.repositories.S3FConfigurationRepository;

public class ConvertData implements ConfigurationActions<String> {

    @Override
    public ResponseEntity<HttpStatus> doActionOnConfiguration(S3FConfigurationRepository configurationRepository,
            MongoTemplate mongoTemplate, String authorization, String correlationToken, String httpValues) {
        try {
            List<S3FConfigurationDto> configurationDtos = configurationRepository.findAll();
            for (S3FConfigurationDto item : configurationDtos) {
                S3FConfigurationDto s3FConfigurationUpdate = new S3FConfigurationDto(item.getId(),
                        EncryptionDecryptionService.encryptKeyValueList(item.getKeyValuePairs()), item.getVersion(),
                        item.getLifecycle(), item.getService());
                configurationRepository.save(s3FConfigurationUpdate);
                LoggerHelper.logData(Level.INFO, "Convert configuration succeeds", correlationToken, authorization,
                        ConvertData.class.getName());
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            LoggerHelper.logData(Level.ERROR, "Convert configuration fails", correlationToken, authorization,
                    ConvertData.class.getName(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
