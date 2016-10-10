package s3f.s3f_configuration.action.constants;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;

import s3f.s3f_configuration.repositories.S3FConfigurationConstantRepository;

public class DeleteConstantAction implements ConstantActions<String> {

    @Override
    public ResponseEntity<?> doActionOnConstant(S3FConfigurationConstantRepository s3fConfigurationConstantRepository,
	    MongoTemplate mongoTemplate, String authorization, String correlationToken, String httpValues) {
	// TODO Auto-generated method stub
	return null;
    }

}
