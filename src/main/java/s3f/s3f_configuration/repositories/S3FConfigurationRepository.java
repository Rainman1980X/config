package s3f.s3f_configuration.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import s3f.s3f_configuration.entities.S3FConfiguration;

import java.util.List;

public interface S3FConfigurationRepository extends MongoRepository<S3FConfiguration, String> {
    List<S3FConfiguration> findByServiceAndVersionAndLifecycle(String service, String version, String lifecycle);
}
