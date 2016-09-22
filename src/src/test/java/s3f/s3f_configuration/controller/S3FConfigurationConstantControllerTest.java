package s3f.s3f_configuration.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import s3f.s3f_configuration.dto.S3FConfigurationConstantDto;
import s3f.s3f_configuration.services.S3FConfigurationConstantService;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

public class S3FConfigurationConstantControllerTest {
    private final String version = "v1";
    private final String lifecycle = "develop";
    private S3FConfigurationConstantService s3FConfigurationConstantService;
    private S3FConfigurationConstantController s3FConfigurationConstantController;

    @Before
    public void setup() {
        s3FConfigurationConstantService = mock(S3FConfigurationConstantService.class);
        s3FConfigurationConstantController = new S3FConfigurationConstantController();
        ReflectionTestUtils.setField(s3FConfigurationConstantController, "s3FConfigurationConstantService", s3FConfigurationConstantService);
    }

    @Test
    public void post() throws Exception {
        final S3FConfigurationConstantDto s3FConfigurationConstantDto = s3FConfigurationConstantDto();
        ResponseEntity responseEntity = s3FConfigurationConstantController.create(s3FConfigurationConstantDto);

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        verify(s3FConfigurationConstantService).create(s3FConfigurationConstantDto);
    }

    @Test
    public void get() throws Exception {
        final S3FConfigurationConstantDto s3FConfigurationConstant = s3FConfigurationConstant();
        when(s3FConfigurationConstantService.read("version", "lifecycle","encMongoDBHost")).thenReturn(s3FConfigurationConstant);
        ResponseEntity responseEntity = s3FConfigurationConstantController.read("version", "lifecycle","encMongoDBHost");

        assertThat(responseEntity.getBody(), is(s3FConfigurationConstant));
    }

    @Test
    public void put() throws Exception {
        ResponseEntity responseEntity = s3FConfigurationConstantController.update(s3FConfigurationConstant());

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        verify(s3FConfigurationConstantService).update(s3FConfigurationConstant());
    }

    private S3FConfigurationConstantDto s3FConfigurationConstantDto() {
        return new S3FConfigurationConstantDto( version, lifecycle,"encMongoDBHost", "$encMongoDBHost");
    }

    private S3FConfigurationConstantDto s3FConfigurationConstant() {
        return new S3FConfigurationConstantDto(version, lifecycle,"encMongoDBHost", "$encMongoDBHost");
    }
}
