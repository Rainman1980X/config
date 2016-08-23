package s3f.s3f_configuration.controller;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import s3f.s3f_configuration.services.S3FConfigurationConstantService;
import s3f.s3f_configuration.services.S3FConfigurationService;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class S3FConfigurationControllerTest {
    @Test
    public void getRoot() throws Exception {
        S3FConfigurationService s3FConfigurationService = mock(S3FConfigurationService.class);
        S3FConfigurationConstantService s3FConfigurationConstantService = mock(S3FConfigurationConstantService.class);
        S3FConfigurationController s3FConfigurationController = new S3FConfigurationController();
        ReflectionTestUtils.setField(s3FConfigurationController, "s3FConfigurationService", s3FConfigurationService);
        ReflectionTestUtils.setField(s3FConfigurationController, "s3FConfigurationConstantService", s3FConfigurationConstantService);

        final String service = "service";
        final String version = "v1";
        final String lifecycle = "production";

        ResponseEntity responseEntity = s3FConfigurationController.getRoot(service, version, lifecycle);

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        verify(s3FConfigurationConstantService).read(version, lifecycle);
        verify(s3FConfigurationService).read(service, version, lifecycle);
    }
}
