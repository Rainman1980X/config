package s3f.s3f_configuration.action.configuration;

import org.apache.log4j.Level;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import s3f.framework.logger.LoggerHelper;
import s3f.s3f_configuration.action.constants.DeleteConstantAction;
import s3f.s3f_configuration.controller.S3FConfigurationController;
import s3f.s3f_configuration.dto.S3FConfigurationDto;
import s3f.s3f_configuration.repositories.S3FConfigurationRepository;

public class DeleteConfigurationAction implements ConfigurationActions<String> {

    @Override
    public ResponseEntity<HttpStatus> doActionOnConfiguration(S3FConfigurationRepository configurationRepository,
            MongoTemplate mongoTemplate, String authorization, String correlationToken, String configurationId) {
        try {
            S3FConfigurationDto s3FConfigurationtDto = configurationRepository.findById(configurationId);
            if (s3FConfigurationtDto == null) {
                LoggerHelper.logData(Level.WARN, "Configuration not found", correlationToken, authorization,
                        DeleteConfigurationAction.class.getName());
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            configurationRepository.delete(s3FConfigurationtDto);
            LoggerHelper.logData(Level.INFO,
                    "delete (single S3FConfiguration) " + s3FConfigurationtDto.getService() + " "
                            + s3FConfigurationtDto.getVersion() + " " + s3FConfigurationtDto.getLifecycle(),
                    correlationToken, authorization, S3FConfigurationController.class.getName());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            LoggerHelper.logData(Level.ERROR, "Configuration not found", correlationToken, authorization,
                    DeleteConstantAction.class.getName(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
