package s3f.s3f_configuration.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import s3f.s3f_configuration.entities.S3FConfigurationConstant;

import java.util.List;

public interface S3FConfigurationConstantRepository extends MongoRepository<S3FConfigurationConstant, String> {
    List<S3FConfigurationConstant> findByVersionLikeAndLifecycleLikeOrderByIdDesc(String version, String lifecycle);

    List<S3FConfigurationConstant> findByVersionLikeAndLifecycleLike(String version, String lifecycle);
}
