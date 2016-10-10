package s3f.s3f_configuration.action.configuration;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;

import s3f.s3f_configuration.repositories.S3FConfigurationRepository;

/**
 * Created by MSBurger on 12.09.2016.
 */
public interface ConfigurationActions<T> {
    public ResponseEntity<?> doActionOnConfiguration(S3FConfigurationRepository configurationRepository,
	    MongoTemplate mongoTemplate, String authorization, String correlationToken, T httpValues);

}
