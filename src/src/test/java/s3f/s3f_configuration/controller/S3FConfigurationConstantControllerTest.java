package s3f.s3f_configuration.controller;

import org.junit.Before;
import org.junit.Test;

import s3f.s3f_configuration.dto.S3FConfigurationConstantDto;

public class S3FConfigurationConstantControllerTest {
    private final String authorization = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIiLCJyb2xlIjoiYWRtaW4iLCJuYmYiOjE0NzQ2Mzc4NjIsImlzcyI6InMzZiIsImV4cCI6NzI4OTU2NDQwMH0.SDuVE3Eg8uV7JXWrrjQAw7c_2NeD4fe7EwjCX4ULZto";
    private final String correlationToken = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIiLCJyb2xlIjoiYWRtaW4iLCJuYmYiOjE0NzQ2Mzc4NjIsImlzcyI6InMzZiIsImV4cCI6NzI4OTU2NDQwMH0.SDuVE3Eg8uV7JXWrrjQAw7c_2NeD4fe7EwjCX4ULZto";
    private final String id = "test_2908233490284ß902842390";
    private final String version = "v1";
    private final String lifecycle = "develop";
    private S3FConfigurationConstantController s3FConfigurationConstantController;

    @Before
    public void setup() {
	s3FConfigurationConstantController = new S3FConfigurationConstantController();
    }

    @Test
    public void post() throws Exception {
	// final S3FConfigurationConstantDto s3FConfigurationConstantDto =
	// s3FConfigurationConstantDto();
	// ResponseEntity responseEntity =
	// s3FConfigurationConstantController.create(authorization,
	// correlationToken,
	// s3FConfigurationConstantDto);
	//
	// assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
	// verify(s3FConfigurationConstantService).create(s3FConfigurationConstantDto);
    }

    @Test
    public void get() throws Exception {
	// final S3FConfigurationConstantDto s3FConfigurationConstant =
	// s3FConfigurationConstant();
	// when(s3FConfigurationConstantService.read("version",
	// "lifecycle","encMongoDBHost")).thenReturn(s3FConfigurationConstant);
	// ResponseEntity responseEntity =
	// s3FConfigurationConstantController.read(authorization,
	// correlationToken,
	// "version", "lifecycle", "encMongoDBHost");
	//
	// assertThat(responseEntity.getBody(), is(s3FConfigurationConstant));
    }

    @Test
    public void put() throws Exception {
	// ResponseEntity responseEntity =
	// s3FConfigurationConstantController.update(authorization,
	// correlationToken,
	// s3FConfigurationConstant());
	//
	// assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
	// verify(s3FConfigurationConstantService).update(s3FConfigurationConstant());
    }

    private S3FConfigurationConstantDto s3FConfigurationConstantDto() {
	return new S3FConfigurationConstantDto(id, version, lifecycle, "encMongoDBHost", "$encMongoDBHost");
    }

    private S3FConfigurationConstantDto s3FConfigurationConstant() {
	return new S3FConfigurationConstantDto(id, version, lifecycle, "encMongoDBHost", "$encMongoDBHost");
    }
}
