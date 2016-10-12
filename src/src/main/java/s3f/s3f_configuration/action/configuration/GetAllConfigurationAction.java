package s3f.s3f_configuration.action.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Level;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import s3f.framework.logger.LoggerHelper;
import s3f.framework.security.EncryptionDecryptionService;
import s3f.s3f_configuration.action.constants.GetAllConstantAction;
import s3f.s3f_configuration.dto.S3FConfigurationDto;
import s3f.s3f_configuration.repositories.S3FConfigurationRepository;

@Service
public class GetAllConfigurationAction implements ConfigurationActions<Map<String, String>> {

    @Override
    public ResponseEntity<List<S3FConfigurationDto>> doActionOnConfiguration(
            S3FConfigurationRepository configurationRepository,
            MongoTemplate mongoTemplate, String authorization, String correlationToken,
            Map<String, String> httpValues) {
        try {
            List<S3FConfigurationDto> configurationDtos;

            if (httpValues.isEmpty()) {
                LoggerHelper.logData(Level.INFO, "Configuration all without any parameters.", correlationToken,
                        authorization, GetAllConstantAction.class.getName());
                configurationDtos = configurationRepository.findAll();
            } else if (hasServiceAndVersionAndLifecycle(httpValues)) {
                LoggerHelper.logData(Level.INFO, "Configuration all with service, version and lifecycle.",
                        correlationToken,
                        authorization, GetAllConstantAction.class.getName());
                configurationDtos = configurationRepository.findByServiceAndVersionAndLifecycle(
                        httpValues.get("service"), httpValues.get("version"), httpValues.get("lifecycle"));
            } else {
                LoggerHelper.logData(Level.WARN, "Configuration Constant wrong combination of version and lifecycle.",
                        correlationToken, authorization, GetAllConstantAction.class.getName());
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }

            if (configurationDtos.isEmpty()) {
                LoggerHelper.logData(Level.WARN, "No configuration constants found.", correlationToken, authorization,
                        GetAllConstantAction.class.getName());
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            List<S3FConfigurationDto> s3FConfigurationConstantDtosEncrypt = new ArrayList<>();
            for (S3FConfigurationDto temp : configurationDtos) {
                s3FConfigurationConstantDtosEncrypt.add(encryptConstantDto(temp));
            }

            LoggerHelper.logData(Level.INFO, "Configuration Constant successful returned.", correlationToken,
                    authorization, GetAllConstantAction.class.getName());
            return new ResponseEntity<>(s3FConfigurationConstantDtosEncrypt, HttpStatus.OK);
        } catch (Exception e) {
            LoggerHelper.logData(Level.ERROR, "Execute of command fails.", correlationToken, authorization,
                    GetAllConstantAction.class.getName(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    private boolean hasServiceAndVersionAndLifecycle(Map<String, String> httpValues) {
        return httpValues.containsKey("service") && httpValues.containsKey("version")
                && httpValues.containsKey("lifecycle");
    }

    private S3FConfigurationDto encryptConstantDto(S3FConfigurationDto decryptValue) {
        return new S3FConfigurationDto(decryptValue.getId(),
                EncryptionDecryptionService.decryptKeyValueList(decryptValue.getKeyValuePairs()),
                decryptValue.getVersion(), decryptValue.getLifecycle(), decryptValue.getService());
    }

}
