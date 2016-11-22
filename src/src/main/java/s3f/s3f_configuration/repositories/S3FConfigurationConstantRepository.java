package s3f.s3f_configuration.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import s3f.s3f_configuration.dto.S3FConfigurationConstantDto;

@Service
@EnableMongoRepositories
public interface S3FConfigurationConstantRepository extends MongoRepository<S3FConfigurationConstantDto, String> {
    List<S3FConfigurationConstantDto> findByVersionAndLifecycleAndConstantName(String version, String lifecycle,
            String constantName);

    List<S3FConfigurationConstantDto> findByVersionAndLifecycle(String version, String lifecycle);
}
