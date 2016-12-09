package s3f.s3f_configuration.action.constants;

import org.apache.catalina.connector.Response;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import s3f.framework.encryption.EncryptionDecryptionService;
import s3f.s3f_configuration.dto.S3FConfigurationConstantDto;
import s3f.s3f_configuration.dto.S3FConfigurationDto;
import s3f.s3f_configuration.repositories.S3FConfigurationConstantRepository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by snbuchho on 22.11.2016.
 */
public class CopyConstantAction implements ConstantActions<Map<String,String>>{

    @Override
    public ResponseEntity<?> doActionOnConstant(S3FConfigurationConstantRepository s3FConfigurationConstantRepository, MongoTemplate mongoTemplate, String authorization, String correlationToken, Map<String, String> httpValues) {
        Map<String,String> sourceConstant = new HashMap<>();
        sourceConstant.put("version", httpValues.get("SourceVersion"));
        sourceConstant.put("lifecycle", httpValues.get("LifecycleToCopyFrom"));
        sourceConstant.put("constantName", httpValues.get("ConstantName"));
        ResponseEntity<?> entity = new GetConstantAction().doActionOnConstant(s3FConfigurationConstantRepository,mongoTemplate,authorization,correlationToken,sourceConstant);
        if(!entity.getStatusCode().is2xxSuccessful())
            return entity;
        S3FConfigurationConstantDto constant = decryptConstantDto((S3FConfigurationConstantDto)entity.getBody());

        Map<String,String> targetConstant = new HashMap<>();
        targetConstant.put("version", httpValues.get("TargetVersion"));
        targetConstant.put("lifecycle", httpValues.get("LifecycleToCopyTo"));
        targetConstant.put("constantName", httpValues.get("ConstantName"));
        entity = new GetConstantAction().doActionOnConstant(s3FConfigurationConstantRepository,mongoTemplate,authorization,correlationToken,targetConstant);
        if(!entity.getStatusCode().is2xxSuccessful()){
            return new CreateConstantAction().doActionOnConstant(s3FConfigurationConstantRepository,mongoTemplate,authorization,correlationToken,new S3FConfigurationConstantDto(null,httpValues.get("TargetVersion"),httpValues.get("LifecycleToCopyTo"),httpValues.get("ConstantName"),constant.getConstantValue()));
        }
        else{
            S3FConfigurationConstantDto constantDto = (S3FConfigurationConstantDto)entity.getBody();
            return new EditConstantAction().doActionOnConstant(s3FConfigurationConstantRepository,mongoTemplate,authorization,correlationToken,new S3FConfigurationConstantDto(constantDto.getId(),httpValues.get("TargetVersion"),httpValues.get("LifecycleToCopyTo"),httpValues.get("ConstantName"),constant.getConstantValue()));
        }
    }
    private S3FConfigurationConstantDto decryptConstantDto(S3FConfigurationConstantDto encryptValue) {
        String constantValuePlain = EncryptionDecryptionService.decrypt(encryptValue.getConstantValue());
        return new S3FConfigurationConstantDto(encryptValue.getId(), encryptValue.getVersion(),
                encryptValue.getLifecycle(), encryptValue.getConstantName(), constantValuePlain);
    }
}
