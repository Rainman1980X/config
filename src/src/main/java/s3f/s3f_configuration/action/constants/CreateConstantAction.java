package s3f.s3f_configuration.action.constants;

import java.util.List;
import java.util.regex.Pattern;

import org.apache.log4j.Level;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import s3f.framework.logger.LoggerHelper;
import s3f.framework.security.EncryptionDecryptionService;
import s3f.s3f_configuration.dto.S3FConfigurationConstantDto;
import s3f.s3f_configuration.repositories.S3FConfigurationConstantRepository;

@Service
public class CreateConstantAction implements ConstantActions<S3FConfigurationConstantDto> {

    private final String pattern = "$env_[a-zA-Z0-9][.a-zA-Z0-9]*";

    private S3FConfigurationConstantRepository s3fConfigurationConstantRepository;

    @Override
    public ResponseEntity<S3FConfigurationConstantDto> doActionOnConstant(
            S3FConfigurationConstantRepository s3fConfigurationConstantRepository, MongoTemplate mongoTemplate,
            String authorization, String correlationToken, S3FConfigurationConstantDto s3fConfigurationConstantDto) {
        this.s3fConfigurationConstantRepository = s3fConfigurationConstantRepository;
        LoggerHelper.logData(Level.INFO, "Create configuration constant", correlationToken, authorization,
                CreateConstantAction.class.getName());
        try {
            if(!isValidName(s3fConfigurationConstantDto.getConstantName())){
                LoggerHelper.logData(Level.WARN, "Constant name does not match pattern: $env_[a-zA-Z0-9][.a-zA-Z0-9]* ", correlationToken,
                        authorization, CreateConstantAction.class.getName());
                return new ResponseEntity<>(HttpStatus.PRECONDITION_REQUIRED);
            }
            if (hasDoubleEntry(s3fConfigurationConstantDto)) {
                LoggerHelper.logData(Level.WARN, "Duplicate entry configuration constant", correlationToken,
                        authorization, CreateConstantAction.class.getName());
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            } else {

                String constantValue = EncryptionDecryptionService
                        .encrypt(s3fConfigurationConstantDto.getConstantValue());

                S3FConfigurationConstantDto s3FConfigurationConstantEnc = new S3FConfigurationConstantDto(
                        s3fConfigurationConstantDto.getId(), s3fConfigurationConstantDto.getVersion(),
                        s3fConfigurationConstantDto.getLifecycle(), s3fConfigurationConstantDto.getConstantName(),
                        constantValue);
                S3FConfigurationConstantDto s3fConfigurationConstantDtoSaved = s3fConfigurationConstantRepository
                        .save(s3FConfigurationConstantEnc);
                s3fConfigurationConstantDtoSaved.setConstantValue(
                        EncryptionDecryptionService.decrypt(s3fConfigurationConstantDtoSaved.getConstantValue()));
                return new ResponseEntity<>(s3fConfigurationConstantDtoSaved, HttpStatus.OK);
            }
        } catch (Exception e) {
            LoggerHelper.logData(Level.INFO, "Create configuration constant fails", correlationToken, authorization,
                    CreateConstantAction.class.getName(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean hasDoubleEntry(S3FConfigurationConstantDto s3fConfigurationConstantDto) {
        final List<S3FConfigurationConstantDto> s3FConfigurationConstantDtos = s3fConfigurationConstantRepository
                .findByVersionAndLifecycleAndConstantName(s3fConfigurationConstantDto.getVersion(),
                        s3fConfigurationConstantDto.getLifecycle(), s3fConfigurationConstantDto.getConstantName());
        return s3FConfigurationConstantDtos.size() > 0;
    }

    private boolean isValidName(String constantName){
        return Pattern.compile(pattern).matcher(constantName).matches();
    }
}
