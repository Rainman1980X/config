package s3f.s3f_configuration.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import s3f.s3f_configuration.dto.S3FConfigurationDto;

@Service
@EnableMongoRepositories
public interface S3FConfigurationRepository extends MongoRepository<S3FConfigurationDto, String> {
    List<S3FConfigurationDto> findByServiceAndVersionAndLifecycle(String service, String version, String lifecycle);

    S3FConfigurationDto findOneByServiceAndVersionAndLifecycle(String service, String version, String lifecycle);

    S3FConfigurationDto findById(String id);
}
