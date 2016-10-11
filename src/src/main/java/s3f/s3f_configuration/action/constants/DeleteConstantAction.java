package s3f.s3f_configuration.action.constants;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Level;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import s3f.framework.logger.LoggerHelper;
import s3f.s3f_configuration.dto.S3FConfigurationConstantDto;
import s3f.s3f_configuration.repositories.S3FConfigurationConstantRepository;

public class DeleteConstantAction implements ConstantActions<Map<String, String>> {

    @Override
    public ResponseEntity<HttpStatus> doActionOnConstant(
	    S3FConfigurationConstantRepository s3fConfigurationConstantRepository, MongoTemplate mongoTemplate,
	    String authorization, String correlationToken, Map<String, String> httpValues) {
	try {

	    final List<S3FConfigurationConstantDto> s3FConfigurationConstantDtos = s3fConfigurationConstantRepository
		    .findByVersionAndLifecycleAndConstantName(httpValues.get("version"), httpValues.get("lifecycle"),
			    httpValues.get("constantName"));
	    if (s3FConfigurationConstantDtos.isEmpty()) {
		LoggerHelper.logData(Level.WARN, "Configuration Constant not found", correlationToken, authorization,
			DeleteConstantAction.class.getName());
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	    for (S3FConfigurationConstantDto itemsToDelete : s3FConfigurationConstantDtos) {
		s3fConfigurationConstantRepository.delete(itemsToDelete);
	    }
	    LoggerHelper.logData(Level.INFO, "Configuration Constant not found", correlationToken, authorization,
		    DeleteConstantAction.class.getName());
	    return new ResponseEntity<>(HttpStatus.OK);
	} catch (Exception e) {
	    LoggerHelper.logData(Level.WARN, "Configuration Constant not found", correlationToken, authorization,
		    DeleteConstantAction.class.getName());
	    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
    }

}
