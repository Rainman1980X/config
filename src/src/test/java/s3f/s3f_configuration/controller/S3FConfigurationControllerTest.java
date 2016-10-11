package s3f.s3f_configuration.controller;

import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import s3f.s3f_configuration.services.S3FConfigurationService;

public class S3FConfigurationControllerTest {
    private S3FConfigurationService s3FConfigurationService;
    private S3FConfigurationController s3FConfigurationController;
    final String id = "test_2908233490284ß902842390";
    final String service = "ka-upload";
    final String version = "v1";
    final String lifecycle = "production";
    final String authorization = "1111";
    final String correlationToken = "333";

    @Before
    public void setUp() {
	s3FConfigurationService = mock(S3FConfigurationService.class);
	s3FConfigurationController = new S3FConfigurationController();
	ReflectionTestUtils.setField(s3FConfigurationController, "s3FConfigurationService", s3FConfigurationService);
    }

    @Test
    public void getRoot() throws Exception {

	// ResponseEntity responseEntity =
	// s3FConfigurationController.getRoot(authorization, correlationToken,
	// service,
	// version, lifecycle);
	//
	// assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
	// verify(s3FConfigurationConstantService).readAll(version, lifecycle);
	// verify(s3FConfigurationService).read(service, version, lifecycle);
    }

    @Test
    public void post() throws Exception {
	// final HashMap<String, String> keyValuePairs = new HashMap<>();
	// keyValuePairs.put("server.port", "30100");
	// final S3FConfigurationDto s3FConfigurationDto = new
	// S3FConfigurationDto(id, keyValuePairs, version, lifecycle,
	// service);
	//
	// ResponseEntity responseEntity =
	// s3FConfigurationController.create(authorization, correlationToken,
	// s3FConfigurationDto);
	//
	// assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
	// verify(s3FConfigurationService).create(s3FConfigurationDto);
    }
}
