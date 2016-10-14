package s3f.s3f_configuration.action.configuration;

import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import s3f.s3f_configuration.action.constants.GetAllConstantAction;
import s3f.s3f_configuration.dto.S3FConfigurationConstantDto;
import s3f.s3f_configuration.dto.S3FConfigurationDto;
import s3f.s3f_configuration.factories.S3FConfigurationRootBuilder;
import s3f.s3f_configuration.repositories.S3FConfigurationConstantRepository;
import s3f.s3f_configuration.repositories.S3FConfigurationRepository;

@Service
public class GetCompiledConfigurationAction {


    public ResponseEntity<List<S3FConfigurationDto>> doActionOnConfiguration(
            S3FConfigurationConstantRepository s3fConfigurationConstantRepository,
            S3FConfigurationRepository configurationRepository, MongoTemplate mongoTemplate, String authorization,
            String correlationToken, Map<String, String> httpsValues) {

        final ResponseEntity<List<S3FConfigurationConstantDto>> configurationConstants = (new GetAllConstantAction())
                .doActionOnConstant(s3fConfigurationConstantRepository, mongoTemplate, authorization, correlationToken,
                        httpsValues);

        final ResponseEntity<List<S3FConfigurationDto>> configurationDtos = (new GetAllConfigurationAction())
                .doActionOnConfiguration(configurationRepository, mongoTemplate, authorization, correlationToken,
                        httpsValues);
        if (hasBody(configurationConstants.getStatusCode()) && hasBody(configurationDtos.getStatusCode())) {
            try {
                List<S3FConfigurationDto> configurationDtosCompiled = S3FConfigurationRootBuilder
                        .build(configurationConstants.getBody(), configurationDtos.getBody());
                return new ResponseEntity<>(configurationDtosCompiled, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        if (!hasBody(configurationConstants.getStatusCode()) && hasBody(configurationDtos.getStatusCode())) {
            return new ResponseEntity<>(configurationDtos.getBody(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    private boolean hasBody(HttpStatus httpStatus) {
        if (httpStatus.equals(HttpStatus.INTERNAL_SERVER_ERROR))
            return false;
        if (httpStatus.equals(HttpStatus.CONFLICT))
            return false;
        if (httpStatus.equals(HttpStatus.NO_CONTENT))
            return false;
        return true;
    }
}
