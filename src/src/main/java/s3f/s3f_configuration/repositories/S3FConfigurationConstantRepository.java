package s3f.s3f_configuration.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import s3f.s3f_configuration.dto.S3FConfigurationConstantDto;

import java.util.List;
public interface S3FConfigurationConstantRepository extends MongoRepository<S3FConfigurationConstantDto, String> {
    List<S3FConfigurationConstantDto> findByVersionAndLifecycleAndConstantName(String version, String lifecycle,String constantName);
    List<S3FConfigurationConstantDto> findByVersionAndLifecycle(String version, String lifecycle);
}
