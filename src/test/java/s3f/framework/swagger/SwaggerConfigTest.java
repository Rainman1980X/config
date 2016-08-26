package s3f.framework.swagger;

import org.junit.Test;

import static org.junit.Assert.*;

public class SwaggerConfigTest {

    @Test
    public void customDocket() throws Exception {
        new SwaggerConfig(new ApiInfoFactory()).customDocket();
    }
}