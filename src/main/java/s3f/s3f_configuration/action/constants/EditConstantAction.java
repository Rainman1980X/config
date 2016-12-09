package s3f.s3f_configuration.action.constants;

import java.util.List;

import org.apache.log4j.Level;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import s3f.framework.logger.LoggerHelper;
import s3f.framework.encryption.EncryptionDecryptionService;
import s3f.s3f_configuration.dto.S3FConfigurationConstantDto;
import s3f.s3f_configuration.repositories.S3FConfigurationConstantRepository;

@Service
public class EditConstantAction implements ConstantActions<S3FConfigurationConstantDto> {

    @Override
    public ResponseEntity<HttpStatus> doActionOnConstant(
            S3FConfigurationConstantRepository s3fConfigurationConstantRepository, MongoTemplate mongoTemplate,
            String authorization, String correlationToken, S3FConfigurationConstantDto s3FConfigurationConstantDto) {
        try {
            final List<S3FConfigurationConstantDto> s3FConfigurationConstantDtos = s3fConfigurationConstantRepository
                    .findByVersionAndLifecycleAndConstantName(s3FConfigurationConstantDto.getVersion(),
                            s3FConfigurationConstantDto.getLifecycle(), s3FConfigurationConstantDto.getConstantName());
            LoggerHelper.logData(Level.INFO, "Edit configuration constant", correlationToken, authorization,
                    CreateConstantAction.class.getName());

            S3FConfigurationConstantDto s3fConfigurationConstantDtoTemp = mongoTemplate
                    .findOne(
                            new Query(
                                    Criteria.where("version").is(s3FConfigurationConstantDto.getVersion())
                                            .andOperator(Criteria.where("lifecycle")
                                                    .is(s3FConfigurationConstantDto.getLifecycle())
                                                    .andOperator(Criteria.where("ConstantName")
                                                            .is(s3FConfigurationConstantDto.getConstantName())))),
                            S3FConfigurationConstantDto.class);

            if (s3fConfigurationConstantDtoTemp == null) {
                LoggerHelper.logData(Level.WARN, "Configuration Constant not found.", correlationToken, authorization,
                        EditConstantAction.class.getName());
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            S3FConfigurationConstantDto s3FConfigurationConstantDtoTemp;
            s3FConfigurationConstantDtoTemp = s3FConfigurationConstantDtos.get(0);

            String constantValue = EncryptionDecryptionService.encrypt(s3FConfigurationConstantDto.getConstantValue());

            S3FConfigurationConstantDto s3FConfigurationConstantEnc = new S3FConfigurationConstantDto(
                    s3FConfigurationConstantDtoTemp.getId(), s3FConfigurationConstantDtoTemp.getVersion(),
                    s3FConfigurationConstantDtoTemp.getLifecycle(), s3FConfigurationConstantDtoTemp.getConstantName(),
                    constantValue);
            s3fConfigurationConstantRepository.save(s3FConfigurationConstantEnc);
            LoggerHelper.logData(Level.WARN, "Configuration Constant successful saved.", correlationToken,
                    authorization, EditConstantAction.class.getName());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            LoggerHelper.logData(Level.ERROR, "Configuration Constant unsuccessful stored", correlationToken,
                    authorization, EditConstantAction.class.getName(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
