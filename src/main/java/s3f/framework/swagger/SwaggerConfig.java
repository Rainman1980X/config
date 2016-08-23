package s3f.framework.swagger;

import com.google.common.base.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private final ApiInfoFactory apiInfoFactory;

    @Autowired
    public SwaggerConfig(ApiInfoFactory apiInfoFactory) {
        this.apiInfoFactory = apiInfoFactory;
    }

    @Bean
    public Docket customDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(startsWithPredicate("/api/"))
                .build()
                .apiInfo(apiInfoFactory.build())
                .useDefaultResponseMessages(false);
    }

    private Predicate<String> startsWithPredicate(String value) {
        return string -> string != null && string.startsWith(value);
    }

}	
