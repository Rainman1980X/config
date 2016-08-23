package s3f.s3f_configuration.entities;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

public class S3FConfigurationTest {
    @Test
    public void notEqual() {
        S3FConfiguration s3FConfigurationKaUpload = new S3FConfiguration("id1", new HashMap<>(), "v1", "develop", "ka-upload");
        S3FConfiguration s3FConfigurationKaArchive = new S3FConfiguration("id1", new HashMap<>(), "v1", "develop", "ka-archive");

        assertThat(s3FConfigurationKaUpload, not(s3FConfigurationKaArchive));
    }

    @Test
    public void isEqual() {
        S3FConfiguration s3FConfigurationKaUpload = new S3FConfiguration("id1", new HashMap<>(), "v1", "develop", "ka-upload");
        S3FConfiguration s3FConfigurationKaUploadClone = new S3FConfiguration("id1", new HashMap<>(), "v1", "develop", "ka-upload");

        assertThat(s3FConfigurationKaUpload, is(s3FConfigurationKaUploadClone));
    }
}
