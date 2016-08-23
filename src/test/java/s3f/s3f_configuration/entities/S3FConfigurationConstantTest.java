package s3f.s3f_configuration.entities;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

public class S3FConfigurationConstantTest {
    @Test
    public void notEqual() {
        Map<String, String> keyValuePairs = new HashMap<>();
        keyValuePairs.put("@[MongoDBHost]", "mongoDBHost");
        S3FConfigurationConstant s3FConfigurationConstantKaUpload = new S3FConfigurationConstant("id1", keyValuePairs, "v1", "develop");
        S3FConfigurationConstant s3FConfigurationConstantKaArchive = new S3FConfigurationConstant("id2", keyValuePairs, "v1", "production");

        assertThat(s3FConfigurationConstantKaUpload, not(s3FConfigurationConstantKaArchive));
    }

    @Test
    public void isEqual() {
        Map<String, String> keyValuePairs = new HashMap<>();
        keyValuePairs.put("@[MongoDBHost]", "mongoDBHost");
        S3FConfigurationConstant s3FConfigurationConstant1 = new S3FConfigurationConstant("id1", keyValuePairs, "v1", "develop");
        S3FConfigurationConstant s3FConfigurationConstant2 = new S3FConfigurationConstant("id1", keyValuePairs, "v1", "develop");

        assertThat(s3FConfigurationConstant1, is(s3FConfigurationConstant2));
    }
}
