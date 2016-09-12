package s3f.framework.config;

import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import s3f.framework.config.ServletConfig;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class ServletConfigTest {

    private final ServletConfig servletConfig = new ServletConfig();

    @Test
    public void containerCustomizer() {
        assertThat(servletConfig.containerCustomizer(), is(notNullValue()));
    }

    @Test
    public void getAvailableTcpPort(){
        ReflectionTestUtils.setField(servletConfig, "availableTcpPort", 8085);

        assertThat(servletConfig.getAvailableTcpPort(), is(8085));
    }
}
