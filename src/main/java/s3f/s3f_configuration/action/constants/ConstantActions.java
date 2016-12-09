package s3f.s3f_configuration.action.constants;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;

import s3f.s3f_configuration.repositories.S3FConfigurationConstantRepository;

/**
 * Created by MSBurger on 12.09.2016.
 */
public interface ConstantActions<T> {
    public ResponseEntity<?> doActionOnConstant(S3FConfigurationConstantRepository s3FConfigurationConstantRepository,
            MongoTemplate mongoTemplate, String authorization, String correlationToken, T httpValues);

}
