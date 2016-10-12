package s3f.s3f_configuration.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import s3f.s3f_configuration.dto.S3FConfigurationConstantDto;
import s3f.s3f_configuration.dto.S3FConfigurationDto;
import s3f.s3f_configuration.factories.S3FConfigurationRootBuilder;

@Service
public class S3FConfigurationService {

    @Autowired
    private S3FConfigurationRootBuilder s3FConfigurationRootFactory;



    public S3FConfigurationDto build(List<S3FConfigurationConstantDto> s3FConfigurationConstants,
            S3FConfigurationDto s3FConfiguration) {
        return s3FConfigurationRootFactory.build(s3FConfigurationConstants, s3FConfiguration);
    }

    public List<S3FConfigurationDto> build(List<S3FConfigurationConstantDto> s3FConfigurationConstantDtos,
            List<S3FConfigurationDto> s3FConfigurations) {
        return s3FConfigurationRootFactory.build(s3FConfigurationConstantDtos, s3FConfigurations);
    }

}
