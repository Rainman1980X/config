package s3f;

import org.apache.log4j.Level;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import s3f.framework.config.S3FPlaceholderConfigurer;
import s3f.framework.interfaces.ApplicationConstants;
import s3f.framework.lifecycle.LifeCycle;
import s3f.framework.lifecycle.LifecycleUrlDictionary;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import s3f.framework.logger.LoggerHelper;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;


@SpringBootApplication
public class Application implements ApplicationConstants {
    private static String[] args;
    public static final String version = "v1";
    public static String lifecycle;
    public final static String serviceName = "sintec.s3f.mi-config";
    public final static boolean useConfigService = false;

    /**
     * lifecycle (i.a. -develop, -test, -stage, -production)
     *
     * @see https://s3f.sintec.de/_layouts/15/start.aspx#/SitePages/Application-Lifecycle.aspx
     */
    public static void main(String[] args) {
        Application.args = args;
        new LifecycleUrlDictionary().check(args);
        Application.lifecycle = new LifecycleUrlDictionary().getKey(args);
        SpringApplication.run(Application.class, args);
        S3FPlaceholderConfigurer.initConfig(new Application());
        LoggerHelper.initialize(new Application());
    }

    @Bean
    public LifeCycle build() {
        String result = "";
        for (String arg : args) {
            if (arg.startsWith("--lifecycle")) {
                result = arg.replaceFirst("--lifecycle", "");
                result = result.substring(1, result.length());
            }
        }
        return new LifeCycle(result);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/v1").allowedOrigins("*");
                registry.addMapping("/api/v1").allowedMethods("POST,PUT, GET, OPTIONS, DELETE");
                registry.addMapping("/api/v1").allowedHeaders("Access-Control-Allow-Headers,Origin, X-Requested-With, Content-Type, Accept,AUTH-TOKEN");

            }
        };
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public String getLifecycle() {
        return lifecycle;
    }

    @Override
    public URL getConfigServerAddress() {
        return null;
    }

    @Override
    public String getConfigServiceAddress() {
        return "";
    }

    @Override
    public Map<String, String> getConfigServiceArguments() {
        return null;
    }

    @Override
    public boolean getUseConfigService() {
        return false;
    }
}
