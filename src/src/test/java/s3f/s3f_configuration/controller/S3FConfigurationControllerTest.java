package s3f.s3f_configuration.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import s3f.s3f_configuration.dto.S3FConfigurationDto;
import s3f.s3f_configuration.services.S3FConfigurationConstantService;
import s3f.s3f_configuration.services.S3FConfigurationService;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class S3FConfigurationControllerTest {
    private S3FConfigurationService s3FConfigurationService;
    private S3FConfigurationConstantService s3FConfigurationConstantService;
    private S3FConfigurationController s3FConfigurationController;
    final String service = "ka-upload";
    final String version = "v1";
    final String lifecycle = "production";

    @Before
    public void setUp() {
        s3FConfigurationService = mock(S3FConfigurationService.class);
        s3FConfigurationConstantService = mock(S3FConfigurationConstantService.class);
        s3FConfigurationController = new S3FConfigurationController();
        ReflectionTestUtils.setField(s3FConfigurationController, "s3FConfigurationService", s3FConfigurationService);
        ReflectionTestUtils.setField(s3FConfigurationController, "s3FConfigurationConstantService", s3FConfigurationConstantService);
    }

    @Test
    public void getRoot() throws Exception {
        ResponseEntity responseEntity = s3FConfigurationController.getRoot(service, version, lifecycle);

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        verify(s3FConfigurationConstantService).readAll(version, lifecycle);
        verify(s3FConfigurationService).read(service, version, lifecycle);
    }

    @Test
    public void post() {
        final HashMap<String, String> keyValuePairs = new HashMap<>();
        keyValuePairs.put("server.port", "30100");
        final S3FConfigurationDto s3FConfigurationDto = new S3FConfigurationDto(keyValuePairs, version, lifecycle, service);

        ResponseEntity responseEntity = s3FConfigurationController.post(s3FConfigurationDto);

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        verify(s3FConfigurationService).create(s3FConfigurationDto);
    }
}
