package s3f.s3f_configuration.services;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import s3f.s3f_configuration.dto.S3FConfigurationConstantDto;
import s3f.s3f_configuration.entities.S3FConfigurationConstant;
import s3f.s3f_configuration.repositories.S3FConfigurationConstantRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

public class S3FConfigurationConstantServiceTest {
    private S3FConfigurationConstantRepository s3FConfigurationConstantRepository;
    private S3FConfigurationConstantService s3FConfigurationConstantService;
    private final String lifecycle = "develop";
    private final String version = "v1";

    @Before
    public void setUp() {
        s3FConfigurationConstantRepository = mock(S3FConfigurationConstantRepository.class);
        s3FConfigurationConstantService = new S3FConfigurationConstantService();
        ReflectionTestUtils.setField(s3FConfigurationConstantService, "s3FConfigurationConstantRepository", s3FConfigurationConstantRepository);
        ReflectionTestUtils.setField(s3FConfigurationConstantService, "encryptionDecryptionService", new EncryptionDecryptionService());
        ReflectionTestUtils.setField(s3FConfigurationConstantService, "escapeService", new EscapeService());
    }

    @Test
    public void create() throws Exception {
        Map<String, String> keyValuePairs = new HashMap<>();
        keyValuePairs.put("encLogin", "$encLogin");
        final S3FConfigurationConstantDto s3FConfigurationConstantDto = new S3FConfigurationConstantDto(keyValuePairs, version, lifecycle);
        final S3FConfigurationConstant s3FConfigurationConstant = new S3FConfigurationConstant(null, keyValuePairs, version, lifecycle);

        s3FConfigurationConstantService.create(s3FConfigurationConstantDto);

        verify(s3FConfigurationConstantRepository).save(s3FConfigurationConstant);
    }

    @Test
    public void update() throws Exception {
        Map<String, String> keyValuePairs = new HashMap<>();
        keyValuePairs.put("encLogin", "$encLogin");

        S3FConfigurationConstant s3FConfigurationConstantFromRequest = new S3FConfigurationConstant(null, keyValuePairs, version, lifecycle);
        S3FConfigurationConstant existingInDB = new S3FConfigurationConstant("id1", new HashMap<>(), version, lifecycle);
        List<S3FConfigurationConstant> s3FConfigurationConstantListFromDB = new ArrayList<>();
        s3FConfigurationConstantListFromDB.add(existingInDB);
        when(s3FConfigurationConstantRepository.findByVersionLikeAndLifecycleLikeOrderByIdDesc(version, lifecycle)).thenReturn(s3FConfigurationConstantListFromDB);

        s3FConfigurationConstantService.update(s3FConfigurationConstantFromRequest);
        S3FConfigurationConstant expectedUpdated = new S3FConfigurationConstant("id1", keyValuePairs, version, lifecycle);

        verify(s3FConfigurationConstantRepository).save(expectedUpdated);
    }

    @Test
    public void read() throws Exception {
        final List<S3FConfigurationConstant> s3FConfigurationConstants = new ArrayList<>();
        final HashMap<String, String> keyValuePairs = new HashMap<>();
        keyValuePairs.put("nothingToEncrypt", "value");
        final S3FConfigurationConstant s3FConfigurationConstant = new S3FConfigurationConstant("1", keyValuePairs, version, lifecycle);
        s3FConfigurationConstants.add(s3FConfigurationConstant);
        when(s3FConfigurationConstantRepository.findByVersionLikeAndLifecycleLike(version, lifecycle)).thenReturn(s3FConfigurationConstants);

        final S3FConfigurationConstant actual = s3FConfigurationConstantService.read(version, lifecycle);

        assertThat(actual, is(s3FConfigurationConstant));
    }

    @Test
    public void readAll() {
        s3FConfigurationConstantService.readAll(version, lifecycle);

        verify(s3FConfigurationConstantRepository).findByVersionLikeAndLifecycleLikeOrderByIdDesc(version, lifecycle);
    }
}