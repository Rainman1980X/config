package s3f.s3f_configuration.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import s3f.framework.security.EncryptionDecryptionService;
import s3f.s3f_configuration.dto.S3FConfigurationConstantDto;
import s3f.s3f_configuration.repositories.S3FConfigurationConstantRepository;

@Service
public class S3FConfigurationConstantService {
    @Autowired
    private S3FConfigurationConstantRepository s3FConfigurationConstantRepository;

    public void create(S3FConfigurationConstantDto s3FConfigurationConstantDto) throws Exception {

	final List<S3FConfigurationConstantDto> s3FConfigurationConstantDtos = s3FConfigurationConstantRepository
		.findByVersionAndLifecycleAndConstantName(s3FConfigurationConstantDto.getVersion(),
			s3FConfigurationConstantDto.getLifecycle(), s3FConfigurationConstantDto.getConstantName());
	if (s3FConfigurationConstantDtos.size() > 0) {
	    throw new Exception("Duplicate entry found for " + s3FConfigurationConstantDtos.get(0).getConstantName());
	} else {

	    String constantValue = EncryptionDecryptionService.encrypt(s3FConfigurationConstantDto.getConstantValue());

	    S3FConfigurationConstantDto s3FConfigurationConstantEnc = new S3FConfigurationConstantDto(
		    s3FConfigurationConstantDto.getId(), s3FConfigurationConstantDto.getVersion(),
		    s3FConfigurationConstantDto.getLifecycle(), s3FConfigurationConstantDto.getConstantName(),
		    constantValue);
	    s3FConfigurationConstantRepository.save(s3FConfigurationConstantEnc);
	}
    }

    public void update(S3FConfigurationConstantDto s3FConfigurationConstantDto) throws Exception {
	final List<S3FConfigurationConstantDto> s3FConfigurationConstantDtos = s3FConfigurationConstantRepository
		.findByVersionAndLifecycleAndConstantName(s3FConfigurationConstantDto.getVersion(),
			s3FConfigurationConstantDto.getLifecycle(), s3FConfigurationConstantDto.getConstantName());
	if (s3FConfigurationConstantDtos.isEmpty()) {
	    throw new Exception("No Configurations constant found");
	} else {

	    S3FConfigurationConstantDto s3FConfigurationConstantDtoTemp;
	    s3FConfigurationConstantDtoTemp = s3FConfigurationConstantDtos.get(0);

	    String constantValue = EncryptionDecryptionService.encrypt(s3FConfigurationConstantDto.getConstantValue());

	    S3FConfigurationConstantDto s3FConfigurationConstantEnc = new S3FConfigurationConstantDto(
		    s3FConfigurationConstantDtoTemp.getId(), s3FConfigurationConstantDtoTemp.getVersion(),
		    s3FConfigurationConstantDtoTemp.getLifecycle(), s3FConfigurationConstantDtoTemp.getConstantName(),
		    constantValue);
	    s3FConfigurationConstantRepository.save(s3FConfigurationConstantEnc);
	}
    }

    public S3FConfigurationConstantDto read(String version, String lifecycle, String constantName) throws Exception {
	final List<S3FConfigurationConstantDto> s3FConfigurationConstantDtos = s3FConfigurationConstantRepository
		.findByVersionAndLifecycleAndConstantName(version, lifecycle, constantName);
	if (s3FConfigurationConstantDtos.isEmpty()) {
	    throw new Exception("No Configurations constant found");
	} else {
	    S3FConfigurationConstantDto s3FConfigurationConstantDto;

	    s3FConfigurationConstantDto = s3FConfigurationConstantDtos.get(0);
	    return encryptConstantDto(s3FConfigurationConstantDto);
	}
    }

    public List<S3FConfigurationConstantDto> readAll(String version, String lifecycle) {
	List<S3FConfigurationConstantDto> s3FConfigurationConstantDtos = s3FConfigurationConstantRepository
		.findByVersionAndLifecycle(version, lifecycle);
	List<S3FConfigurationConstantDto> s3FConfigurationConstantDtosEncrypt = new ArrayList<>();
	for (S3FConfigurationConstantDto temp : s3FConfigurationConstantDtos) {
	    s3FConfigurationConstantDtosEncrypt.add(encryptConstantDto(temp));
	}
	return s3FConfigurationConstantDtosEncrypt;
    }

    public List<S3FConfigurationConstantDto> readAll() {
	List<S3FConfigurationConstantDto> s3FConfigurationConstantDtos = s3FConfigurationConstantRepository.findAll();
	List<S3FConfigurationConstantDto> s3FConfigurationConstantDtosEncrypt = new ArrayList<>();
	for (S3FConfigurationConstantDto temp : s3FConfigurationConstantDtos) {
	    s3FConfigurationConstantDtosEncrypt.add(encryptConstantDto(temp));
	}
	return s3FConfigurationConstantDtosEncrypt;
    }

    public void remove(String version, String lifecycle, String constantName) throws Exception {
	final List<S3FConfigurationConstantDto> s3FConfigurationConstantDtos = s3FConfigurationConstantRepository
		.findByVersionAndLifecycleAndConstantName(version, lifecycle, constantName);
	if (s3FConfigurationConstantDtos.isEmpty()) {
	    throw new Exception("No Configurations constant found");
	}
	for (S3FConfigurationConstantDto itemsToDelete : s3FConfigurationConstantDtos) {
	    s3FConfigurationConstantRepository.delete(itemsToDelete);
	}
    }

    private S3FConfigurationConstantDto encryptConstantDto(S3FConfigurationConstantDto decryptValue) {
	String constantValuePlain = EncryptionDecryptionService.decrypt(decryptValue.getConstantValue());
	return new S3FConfigurationConstantDto(decryptValue.getId(), decryptValue.getVersion(),
		decryptValue.getLifecycle(), decryptValue.getConstantName(), constantValuePlain);
    }

}
