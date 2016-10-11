package s3f.s3f_configuration.action.constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Level;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import s3f.framework.logger.LoggerHelper;
import s3f.framework.security.EncryptionDecryptionService;
import s3f.s3f_configuration.dto.S3FConfigurationConstantDto;
import s3f.s3f_configuration.repositories.S3FConfigurationConstantRepository;

public class ReadAllConstantAction implements ConstantActions<Map<String, String>> {

    @Override
    public ResponseEntity<List<S3FConfigurationConstantDto>> doActionOnConstant(
	    S3FConfigurationConstantRepository s3fConfigurationConstantRepository, MongoTemplate mongoTemplate,
	    String authorization, String correlationToken, Map<String, String> httpValues) {

	try {
	    List<S3FConfigurationConstantDto> s3FConfigurationConstantDtos;

	    if (httpValues.isEmpty()) {
		LoggerHelper.logData(Level.INFO, "Configuration Constant all without any parameters.", correlationToken,
			authorization, ReadAllConstantAction.class.getName());
		s3FConfigurationConstantDtos = s3fConfigurationConstantRepository.findAll();
	    } else {
		LoggerHelper.logData(Level.INFO, "Configuration Constant all with version and lifecycle.",
			correlationToken, authorization, ReadAllConstantAction.class.getName());
		s3FConfigurationConstantDtos = s3fConfigurationConstantRepository
			.findByVersionAndLifecycle(httpValues.get("version"), httpValues.get("lifecycle"));
	    }

	    if (s3FConfigurationConstantDtos.isEmpty()) {
		LoggerHelper.logData(Level.WARN, "No configuration constants found.", correlationToken, authorization,
			ReadAllConstantAction.class.getName());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    }

	    List<S3FConfigurationConstantDto> s3FConfigurationConstantDtosEncrypt = new ArrayList<>();
	    for (S3FConfigurationConstantDto temp : s3FConfigurationConstantDtos) {
		s3FConfigurationConstantDtosEncrypt.add(encryptConstantDto(temp));
	    }

	    LoggerHelper.logData(Level.INFO, "Configuration Constant successful returned.", correlationToken,
		    authorization, ReadAllConstantAction.class.getName());
	    return new ResponseEntity<>(s3FConfigurationConstantDtosEncrypt, HttpStatus.OK);
	} catch (Exception e) {
	    LoggerHelper.logData(Level.ERROR, "Execute of command fails.", correlationToken, authorization,
		    ReadAllConstantAction.class.getName(), e);
	    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

    }

    private S3FConfigurationConstantDto encryptConstantDto(S3FConfigurationConstantDto decryptValue) {
	String constantValuePlain = EncryptionDecryptionService.decrypt(decryptValue.getConstantValue());
	return new S3FConfigurationConstantDto(decryptValue.getId(), decryptValue.getVersion(),
		decryptValue.getLifecycle(), decryptValue.getConstantName(), constantValuePlain);
    }
}
