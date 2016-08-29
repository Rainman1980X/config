package s3f.framework.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;

@Component
public class ApiInfoFactory {
    @Value("${s3f.swagger2.contactEmail}")
    private String contactEmail;
    @Value("${s3f.swagger2.contactName}")
    private String contactName;
    @Value("${s3f.swagger2.contactUrl}")
    private String contactUrl;
    @Value("${s3f.swagger2.description}")
    private String description;
    @Value("${s3f.swagger2.license}")
    private String license;
    @Value("${s3f.swagger2.licenseUrl}")
    private String licenseUrl;
    @Value("${s3f.swagger2.termsOfServcieUrl}")
    private String termsOfServiceUrl;
    @Value("${s3f.swagger2.title}")
    private String title;
    @Value("${s3f.swagger2.version}")
    private String version;


    public ApiInfo build() {
        ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();
        apiInfoBuilder.contact(new Contact(contactName, contactUrl, contactEmail));
        apiInfoBuilder.description(description);
        apiInfoBuilder.license(license);
        apiInfoBuilder.licenseUrl(licenseUrl);
        apiInfoBuilder.termsOfServiceUrl(termsOfServiceUrl);
        apiInfoBuilder.title(title);
        apiInfoBuilder.version(version);
        return apiInfoBuilder.build();
    }
}