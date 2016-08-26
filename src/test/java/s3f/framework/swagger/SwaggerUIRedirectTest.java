package s3f.framework.swagger;

import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;
import static org.mockito.Matchers.isNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SwaggerUIRedirectTest {

    @Test
    public void redirect() throws Exception {
        final HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
        when(httpServletRequest.getRequestURL()).thenReturn(new StringBuffer("swagger"));

        ModelAndView modelAndView = new SwaggerUIRedirect().redirect(httpServletRequest);

        assertThat(modelAndView, not(nullValue()));
    }
}