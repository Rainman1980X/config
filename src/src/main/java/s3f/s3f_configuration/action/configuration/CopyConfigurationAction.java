package s3f.s3f_configuration.action.configuration;

import org.apache.catalina.connector.Response;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import s3f.s3f_configuration.dto.S3FConfigurationDto;
import s3f.s3f_configuration.repositories.S3FConfigurationRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by snbuchho on 22.11.2016.
 */
public class CopyConfigurationAction implements ConfigurationActions<Map<String, String>>{


    @Override
    public ResponseEntity<?> doActionOnConfiguration(S3FConfigurationRepository configurationRepository, MongoTemplate mongoTemplate, String authorization, String correlationToken, Map<String, String> httpValues) {
        Map<String,String> sourceVersion = new HashMap<>();
        sourceVersion.put("version",httpValues.get("SourceVersion"));
        sourceVersion.put("service",httpValues.get("ServiceName"));
        sourceVersion.put("lifecycle",httpValues.get("LifecycleToCopyFrom"));
        ResponseEntity<List<S3FConfigurationDto>> responseEntity = (new GetAllConfigurationAction()).doActionOnConfiguration(configurationRepository, mongoTemplate,
                authorization, correlationToken, sourceVersion);
        if(!responseEntity.getStatusCode().is2xxSuccessful()||responseEntity.getBody().size()==0){
            return new ResponseEntity<String>("Specified source configuration does not exist",HttpStatus.BAD_REQUEST);
        }
        S3FConfigurationDto sourceConfigDto = responseEntity.getBody().get(0);
        Map<String,String> targetVersion = new HashMap<>();
        targetVersion.put("version",httpValues.get("TargetVersion"));
        targetVersion.put("service",httpValues.get("ServiceName"));
        targetVersion.put("lifecycle",httpValues.get("LifecycleToCopyTo"));
        responseEntity = (new GetAllConfigurationAction()).doActionOnConfiguration(configurationRepository, mongoTemplate,
                authorization, correlationToken, targetVersion);
        if(!responseEntity.getStatusCode().is2xxSuccessful()||responseEntity.getBody().size()==0){
            return (new CreateConfigurationAction()).doActionOnConfiguration(configurationRepository, mongoTemplate,
                    authorization, correlationToken, new S3FConfigurationDto("",sourceConfigDto.getKeyValuePairs(),httpValues.get("TargetVersion"),httpValues.get("LifecycleToCopyTo"),httpValues.get("ServiceName")));
        }
        else{
            S3FConfigurationDto targetConfigDto = responseEntity.getBody().get(0);
            return (new EditConfigurationAction()).doActionOnConfiguration(configurationRepository, mongoTemplate,
                    authorization, correlationToken, new S3FConfigurationDto(targetConfigDto.getId(),sourceConfigDto.getKeyValuePairs(),targetConfigDto.getVersion(),targetConfigDto.getLifecycle(),targetConfigDto.getService()));
        }
    }
}
