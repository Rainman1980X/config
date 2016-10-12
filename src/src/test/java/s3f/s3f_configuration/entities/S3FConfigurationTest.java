package s3f.s3f_configuration.entities;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.HashMap;

import org.junit.Test;

import s3f.s3f_configuration.dto.S3FConfigurationDto;

public class S3FConfigurationTest {
    @Test
    public void notEqual() {
	S3FConfigurationDto s3FConfigurationKaUpload = new S3FConfigurationDto("id1", new HashMap<>(), "v1", "develop",
		"ka-upload");
	S3FConfigurationDto s3FConfigurationKaArchive = new S3FConfigurationDto("id1", new HashMap<>(), "v1", "develop",
		"ka-archive");

	assertThat(s3FConfigurationKaUpload, not(s3FConfigurationKaArchive));
    }

    @Test
    public void isEqual() {
	S3FConfigurationDto s3FConfigurationKaUpload = new S3FConfigurationDto("id1", new HashMap<>(), "v1", "develop",
		"ka-upload");
	S3FConfigurationDto s3FConfigurationKaUploadClone = new S3FConfigurationDto("id1", new HashMap<>(), "v1",
		"develop", "ka-upload");

	assertThat(s3FConfigurationKaUpload, is(s3FConfigurationKaUploadClone));
    }
}
