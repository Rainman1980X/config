package s3f.framework.swagger;

import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ApiInfoFactoryTest {
    @Test
    public void build() {
        ApiInfoFactory apiInfoFactory = new ApiInfoFactory();
        ReflectionTestUtils.setField(apiInfoFactory, "contactEmail", "contactEmail");
        ReflectionTestUtils.setField(apiInfoFactory, "contactName", "contactName");
        ReflectionTestUtils.setField(apiInfoFactory, "contactUrl", "contactUrl");
        ReflectionTestUtils.setField(apiInfoFactory, "description", "description");
        ReflectionTestUtils.setField(apiInfoFactory, "license", "license");
        ReflectionTestUtils.setField(apiInfoFactory, "licenseUrl", "licenseUrl");
        ReflectionTestUtils.setField(apiInfoFactory, "termsOfServiceUrl", "termsOfServiceUrl");
        ReflectionTestUtils.setField(apiInfoFactory, "title", "title");
        ReflectionTestUtils.setField(apiInfoFactory, "version", "version");

        ApiInfo apiInfo = apiInfoFactory.build();

        Contact contact = apiInfo.getContact();
        assertThat(contact.getEmail(), is("contactEmail"));
        assertThat(contact.getName(), is("contactName"));
        assertThat(contact.getUrl(), is("contactUrl"));
        assertThat(apiInfo.getDescription(), is("description"));
        assertThat(apiInfo.getLicense(), is("license"));
        assertThat(apiInfo.getLicenseUrl(), is("licenseUrl"));
        assertThat(apiInfo.getTermsOfServiceUrl(), is("termsOfServiceUrl"));
        assertThat(apiInfo.getTitle(), is("title"));
        assertThat(apiInfo.getVersion(), is("version"));
    }

}
