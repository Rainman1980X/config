package s3f.s3f_configuration.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import s3f.s3f_configuration.dto.S3FConfigurationConstantDto;
import s3f.s3f_configuration.entities.S3FConfigurationConstant;
import s3f.s3f_configuration.repositories.S3FConfigurationConstantRepository;

import java.util.List;
import java.util.Map;

@Service
public class S3FConfigurationConstantService {
    @Autowired
    private S3FConfigurationConstantRepository s3FConfigurationConstantRepository;
    @Autowired
    private EncryptionDecryptionService encryptionDecryptionService;
    @Autowired
    private EscapeService escapeService;

    public void create(S3FConfigurationConstantDto s3FConfigurationConstantDto) throws Exception {
        Map<String, String> encryptedKeyValuePairs = encryptionDecryptionService.encrypt(s3FConfigurationConstantDto.getKeyValuePairs());
        S3FConfigurationConstant s3FConfigurationConstantEnc = new S3FConfigurationConstant(null,
                escapeService.escape(encryptedKeyValuePairs),
                s3FConfigurationConstantDto.getVersion(),
                s3FConfigurationConstantDto.getLifecycle());
        s3FConfigurationConstantRepository.save(s3FConfigurationConstantEnc);
    }

    public void update(S3FConfigurationConstant s3FConfigurationConstant) throws Exception {
        S3FConfigurationConstant s3FConfigurationConstantFromMongo = s3FConfigurationConstantRepository.findByVersionLikeAndLifecycleLikeOrderByIdDesc(s3FConfigurationConstant.getVersion(), s3FConfigurationConstant.getLifecycle()).get(0);
        Map<String, String> encryptedKeyValuePairs = encryptionDecryptionService.encrypt(s3FConfigurationConstant.getKeyValuePairs());
        S3FConfigurationConstant s3FConfigurationConstantEnc = new S3FConfigurationConstant(
                s3FConfigurationConstantFromMongo.getId(),
                escapeService.escape(encryptedKeyValuePairs),
                s3FConfigurationConstant.getVersion(),
                s3FConfigurationConstant.getLifecycle());
        s3FConfigurationConstantRepository.save(s3FConfigurationConstantEnc);
    }

    public S3FConfigurationConstant read(String version, String lifecycle) throws Exception {
        final List<S3FConfigurationConstant> s3FConfigurationConstants = s3FConfigurationConstantRepository.findByVersionLikeAndLifecycleLike(version, lifecycle);
        if (s3FConfigurationConstants.size() == 0) {
            throw new Exception("No S3FConfigurationConstant in DB");
        }
        final S3FConfigurationConstant s3FConfigurationConstantFromMongo = s3FConfigurationConstants.get(s3FConfigurationConstants.size() - 1);
        Map<String, String> decryptedKeyValuePairs = encryptionDecryptionService.decrypt(s3FConfigurationConstantFromMongo.getKeyValuePairs());
        return new S3FConfigurationConstant(
                s3FConfigurationConstantFromMongo.getId(),
                escapeService.unescape(decryptedKeyValuePairs),
                s3FConfigurationConstantFromMongo.getVersion(),
                s3FConfigurationConstantFromMongo.getLifecycle());
    }

    public List<S3FConfigurationConstant> readAll(String version, String lifecycle) {
        return s3FConfigurationConstantRepository.findByVersionLikeAndLifecycleLikeOrderByIdDesc(version, lifecycle);
    }
}
