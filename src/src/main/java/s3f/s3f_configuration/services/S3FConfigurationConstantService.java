package s3f.s3f_configuration.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import s3f.s3f_configuration.dto.S3FConfigurationConstantDto;
import s3f.s3f_configuration.repositories.S3FConfigurationConstantRepository;

import java.util.List;

//Todo Klärung weshalb nicht mehr escaped werden muss
@Service
public class S3FConfigurationConstantService {
    @Autowired
    private S3FConfigurationConstantRepository s3FConfigurationConstantRepository;
    @Autowired
    private EncryptionDecryptionService encryptionDecryptionService;
    @Autowired
    private EscapeService escapeService;

    public void create(S3FConfigurationConstantDto s3FConfigurationConstantDto) throws Exception {

        String constantValue = encryptionDecryptionService.encrypt(s3FConfigurationConstantDto.getConstantValue());

        S3FConfigurationConstantDto s3FConfigurationConstantEnc = new S3FConfigurationConstantDto(
                s3FConfigurationConstantDto.getVersion(),
                s3FConfigurationConstantDto.getLifecycle(),
                s3FConfigurationConstantDto.getConstantName(),
                constantValue);
        s3FConfigurationConstantRepository.save(s3FConfigurationConstantEnc);
    }

    public void update(S3FConfigurationConstantDto s3FConfigurationConstantDto) throws Exception {
        final List<S3FConfigurationConstantDto> s3FConfigurationConstantDtos =
                s3FConfigurationConstantRepository.findByVersionAndLifecycleAndConstantName(
                s3FConfigurationConstantDto.getVersion(),
                s3FConfigurationConstantDto.getLifecycle(),
                s3FConfigurationConstantDto.getConstantName());
        if(s3FConfigurationConstantDtos.isEmpty()){
            throw new Exception("No Configurations constant found");
        }
        S3FConfigurationConstantDto s3FConfigurationConstantDtoTemp;
        if(s3FConfigurationConstantDtos.size() >1){
            throw new Exception("Duplicate entry found for "+s3FConfigurationConstantDtos.get(0).getConstantName());
        }

        s3FConfigurationConstantDtoTemp = s3FConfigurationConstantDtos.get(0);

        String constantValue = encryptionDecryptionService.encrypt(s3FConfigurationConstantDto.getConstantValue());

        S3FConfigurationConstantDto s3FConfigurationConstantEnc = new S3FConfigurationConstantDto(
                s3FConfigurationConstantDtoTemp.getId(),
                s3FConfigurationConstantDtoTemp.getVersion(),
                s3FConfigurationConstantDtoTemp.getLifecycle(),
                s3FConfigurationConstantDtoTemp.getConstantName(),
                constantValue);
        s3FConfigurationConstantRepository.save(s3FConfigurationConstantEnc);
    }

    public S3FConfigurationConstantDto read(String version, String lifecycle,String constantName) throws Exception {
        final List<S3FConfigurationConstantDto> s3FConfigurationConstantDtos = s3FConfigurationConstantRepository.findByVersionAndLifecycleAndConstantName(version, lifecycle,constantName);
        if(s3FConfigurationConstantDtos.isEmpty()){
            throw new Exception("No Configurations constant found");
        }
        S3FConfigurationConstantDto s3FConfigurationConstantDto;
        if(s3FConfigurationConstantDtos.size() >1){
            throw new Exception("Duplicate entry found for "+s3FConfigurationConstantDtos.get(0).getConstantName());
        }
        s3FConfigurationConstantDto = s3FConfigurationConstantDtos.get(0);
        String constantValuePlain = encryptionDecryptionService.decrypt(s3FConfigurationConstantDto.getConstantValue());
        return new S3FConfigurationConstantDto(
                s3FConfigurationConstantDto.getVersion(),
                s3FConfigurationConstantDto.getLifecycle(),
                s3FConfigurationConstantDto.getConstantName(),
                constantValuePlain);
    }

    public List<S3FConfigurationConstantDto> readAll(String version, String lifecycle) {
        return s3FConfigurationConstantRepository.findByVersionAndLifecycle(version, lifecycle);
    }
}
