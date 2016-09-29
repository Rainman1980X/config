package s3f.s3f_configuration.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import s3f.s3f_configuration.dto.S3FConfigurationConstantDto;
import s3f.s3f_configuration.dto.S3FConfigurationDto;
import s3f.s3f_configuration.dto.S3FConfigurationRootDto;
import s3f.s3f_configuration.entities.S3FConfiguration;
import s3f.s3f_configuration.factories.S3FConfigurationRootFactory;
import s3f.s3f_configuration.repositories.S3FConfigurationRepository;

@Service
public class S3FConfigurationService {
	@Autowired
	private S3FConfigurationRepository s3FConfigurationRepository;

	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private S3FConfigurationRootFactory s3FConfigurationRootFactory;

	public void create(S3FConfigurationDto s3FConfigurationDto) throws Exception {

		try {
			if (readAll(s3FConfigurationDto.getVersion(), s3FConfigurationDto.getLifecycle(),
					s3FConfigurationDto.getService()).size() > 0) {
				throw new Exception("Duplicate entry");
			} else {
				S3FConfiguration s3FConfiguration = new S3FConfiguration(null, s3FConfigurationDto.getKeyValuePairs(),
						s3FConfigurationDto.getVersion(), s3FConfigurationDto.getLifecycle(),
						s3FConfigurationDto.getService());
				s3FConfigurationRepository.insert(s3FConfiguration);
			}

		} catch (Exception e) {
			throw e;
		}
	}

	public List<S3FConfiguration> readAll(String service, String version, String lifecycle) {

		return mongoTemplate.find(
				new Query(Criteria.where("service").is(service).andOperator(
						Criteria.where("version").is(version).andOperator(Criteria.where("lifecycle").is(lifecycle)))),
				S3FConfiguration.class);
	}

	public List<S3FConfiguration> readAll() {
		return s3FConfigurationRepository.findAll();
	}

	public S3FConfiguration read(String service, String version, String lifecycle) throws Exception {
		final List<S3FConfiguration> s3FConfigurations = readAll(service, version, lifecycle);
		if (s3FConfigurations.size() == 0) {
			throw new Exception("No S3FConfiguration in DB");
		} else {
			final S3FConfiguration s3FConfiguration = s3FConfigurations.get(s3FConfigurations.size() - 1);
			final S3FConfiguration unescaped = new S3FConfiguration(s3FConfiguration.getId(),
					s3FConfiguration.getKeyValuePairs(), s3FConfiguration.getVersion(), s3FConfiguration.getLifecycle(),
					s3FConfiguration.getService());
			return unescaped;
		}
	}

	public void delete(String service, String version, String lifecycle) throws Exception {
		final List<S3FConfiguration> s3FConfigurations = readAll(service, version, lifecycle);
		if (s3FConfigurations.size() <= 0) {
			throw new Exception("No S3FConfiguration in DB");
		} else {
			for (S3FConfiguration itemToFind : s3FConfigurations) {
				s3FConfigurationRepository.delete(itemToFind.getId());
			}
		}
	}

	public S3FConfigurationRootDto build(List<S3FConfigurationConstantDto> s3FConfigurationConstants,
			S3FConfiguration s3FConfiguration) {
		return s3FConfigurationRootFactory.build(s3FConfigurationConstants, s3FConfiguration);
	}

	public List<S3FConfigurationRootDto> build(List<S3FConfigurationConstantDto> s3FConfigurationConstantDtos,
			List<S3FConfiguration> s3FConfigurations) {
		return s3FConfigurationRootFactory.build(s3FConfigurationConstantDtos, s3FConfigurations);
	}

	public void update(S3FConfiguration s3FConfiguration) throws Exception {
		final List<S3FConfiguration> s3FConfigurations = readAll(s3FConfiguration.getService(),
				s3FConfiguration.getVersion(), s3FConfiguration.getLifecycle());
		if (s3FConfigurations.size() != 1) {
			throw new Exception("Unexpected count of configurations");
		} else {
			S3FConfiguration s3FConfigurationUpdate = new S3FConfiguration(s3FConfiguration.getId(),
					s3FConfiguration.getKeyValuePairs(), s3FConfiguration.getVersion(), s3FConfiguration.getLifecycle(),
					s3FConfiguration.getService());
			s3FConfigurationRepository.save(s3FConfigurationUpdate);
		}
	}
}
