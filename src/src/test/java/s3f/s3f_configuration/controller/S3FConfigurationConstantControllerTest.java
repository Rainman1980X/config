package s3f.s3f_configuration.controller;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import s3f.s3f_configuration.dto.S3FConfigurationConstantDto;
import s3f.s3f_configuration.entities.S3FConfigurationConstant;
import s3f.s3f_configuration.services.S3FConfigurationConstantService;

import java.util.HashMap;
import java.util.Map;

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
        ResponseEntity responseEntity = s3FConfigurationConstantController.post(s3FConfigurationConstantDto);

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        verify(s3FConfigurationConstantService).create(s3FConfigurationConstantDto);
    }

    @Test
    public void get() throws Exception {
        final S3FConfigurationConstant s3FConfigurationConstant = s3FConfigurationConstant();
        when(s3FConfigurationConstantService.read("version", "lifecycle")).thenReturn(s3FConfigurationConstant);
        ResponseEntity responseEntity = s3FConfigurationConstantController.get("version", "lifecycle");

        assertThat(responseEntity.getBody(), is(s3FConfigurationConstant));
    }

    @Test
    public void put() throws Exception {
        ResponseEntity responseEntity = s3FConfigurationConstantController.put(s3FConfigurationConstant());

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        verify(s3FConfigurationConstantService).update(s3FConfigurationConstant());
    }

    private S3FConfigurationConstantDto s3FConfigurationConstantDto() {
        return new S3FConfigurationConstantDto(keyValuePairs(), version, lifecycle);
    }

    private S3FConfigurationConstant s3FConfigurationConstant() {
        return new S3FConfigurationConstant("ID", keyValuePairs(), version, lifecycle);
    }

    private Map<String, String> keyValuePairs() {
        Map<String, String> keyValuePairs = new HashMap<>();
        keyValuePairs.put("encMongoDBHost", "$encMongoDBHost");
        return keyValuePairs;
    }

}
