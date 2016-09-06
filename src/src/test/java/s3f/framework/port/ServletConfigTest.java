package s3f.framework.port;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class ServletConfigTest {
    @Test
    public void containerCustomizer() {
        assertThat(new ServletConfig().containerCustomizer(), is(notNullValue()));
    }
}
