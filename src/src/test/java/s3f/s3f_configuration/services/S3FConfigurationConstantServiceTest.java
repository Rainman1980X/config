package s3f.s3f_configuration.services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import s3f.s3f_configuration.dto.S3FConfigurationConstantDto;
import s3f.s3f_configuration.repositories.S3FConfigurationConstantRepository;

public class S3FConfigurationConstantServiceTest {
    private S3FConfigurationConstantRepository s3FConfigurationConstantRepository;
    private S3FConfigurationConstantService s3FConfigurationConstantService;
    private final String id = "test_2908233490284ß902842390";
    private final String lifecycle = "develop";
    private final String version = "v1";

    @Before
    public void setUp() {
        s3FConfigurationConstantRepository = mock(S3FConfigurationConstantRepository.class);
        s3FConfigurationConstantService = new S3FConfigurationConstantService();
        ReflectionTestUtils.setField(s3FConfigurationConstantService, "s3FConfigurationConstantRepository", s3FConfigurationConstantRepository);
    }

    @Test
    public void create() throws Exception {
	// final S3FConfigurationConstantDto s3FConfigurationConstantDto = new
	// S3FConfigurationConstantDto(id, version,
	// lifecycle, "encLogin", "$encLogin");
	// final S3FConfigurationConstantDto s3FConfigurationConstantDtoEncrypt
	// = new S3FConfigurationConstantDto(id,
	// version, lifecycle, "encLogin", "ZKEuInxN6kZS6IR3IlOBfg==");
	//
	// s3FConfigurationConstantService.create(s3FConfigurationConstantDto);
	//
	// verify(s3FConfigurationConstantRepository).save(s3FConfigurationConstantDtoEncrypt);
    }

    @Test
    public void update() throws Exception {
	S3FConfigurationConstantDto s3FConfigurationConstantFromRequest = new S3FConfigurationConstantDto(id, version,
		lifecycle, "encLogin", "$encLogin");
	S3FConfigurationConstantDto existingInDB = new S3FConfigurationConstantDto(id, version, lifecycle, "encLogin",
		"$encLogin");
        List<S3FConfigurationConstantDto> s3FConfigurationConstantListFromDB = new ArrayList<>();
        s3FConfigurationConstantListFromDB.add(existingInDB);
        when(s3FConfigurationConstantRepository.findByVersionAndLifecycleAndConstantName(version, lifecycle,"encLogin")).thenReturn(s3FConfigurationConstantListFromDB);

        s3FConfigurationConstantService.update(s3FConfigurationConstantFromRequest);
	S3FConfigurationConstantDto expectedUpdated = new S3FConfigurationConstantDto(id, version, lifecycle,
		"encLogin", "ZKEuInxN6kZS6IR3IlOBfg==");

        verify(s3FConfigurationConstantRepository).save(expectedUpdated);
    }
}