package s3f.s3f_configuration.entities;

import org.junit.Test;
import s3f.s3f_configuration.dto.S3FConfigurationConstantDto;

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
        S3FConfigurationConstantDto s3FConfigurationConstantKaUpload = new S3FConfigurationConstantDto("v1", "develop","@[MongoDBHost]", "mongoDBHost");
        S3FConfigurationConstantDto s3FConfigurationConstantKaArchive = new S3FConfigurationConstantDto("v1", "production","@[MongoDBHost]", "mongoDBHost");

        assertThat(s3FConfigurationConstantKaUpload, not(s3FConfigurationConstantKaArchive));
    }

    @Test
    public void isEqual() {
        S3FConfigurationConstantDto s3FConfigurationConstant1 = new S3FConfigurationConstantDto("v1", "develop","@[MongoDBHost]", "mongoDBHost");
        S3FConfigurationConstantDto s3FConfigurationConstant2 = new S3FConfigurationConstantDto("v1", "develop","@[MongoDBHost]", "mongoDBHost");

        assertThat(s3FConfigurationConstant1, is(s3FConfigurationConstant2));
    }
}
