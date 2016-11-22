package s3f.s3f_configuration.action.constants;

import org.apache.log4j.Level;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import s3f.framework.logger.LoggerHelper;
import s3f.s3f_configuration.dto.S3FConfigurationConstantDto;
import s3f.s3f_configuration.repositories.S3FConfigurationConstantRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by snbuchho on 22.11.2016.
 */
public class GetConstantAction  implements ConstantActions<Map<String,String>>{

    @Override
    public ResponseEntity<?> doActionOnConstant(S3FConfigurationConstantRepository s3FConfigurationConstantRepository, MongoTemplate mongoTemplate, String authorization, String correlationToken, Map<String, String> httpValues) {
                LoggerHelper.logData(Level.INFO, "Configuration Constant all without any parameters.", correlationToken,
                        authorization, GetAllConstantAction.class.getName());
                List<S3FConfigurationConstantDto> s3FConfigurationConstantDtos = s3FConfigurationConstantRepository.findByVersionAndLifecycleAndConstantName(httpValues.get("version"),httpValues.get("lifecycle"),httpValues.get("constantName"));
                if(s3FConfigurationConstantDtos==null||s3FConfigurationConstantDtos.size()==0){
                    return new ResponseEntity<String>("Configuration Constant does not exist",HttpStatus.BAD_REQUEST);
                }
                return new ResponseEntity<S3FConfigurationConstantDto>(s3FConfigurationConstantDtos.get(0),HttpStatus.OK);
    }
}
