package s3f;

import java.net.URL;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import s3f.framework.interfaces.ApplicationConstants;
import s3f.framework.lifecycle.LifeCycle;
import s3f.framework.lifecycle.LifecycleUrlDictionary;

@SpringBootApplication
public class Application implements ApplicationConstants {
    private static String[] args;
    public static final String version = "v1";
    public static String lifecycle;
    public final static String serviceName = "sintec.s3f.mi-config";

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

    @Override
    public String getServiceName() {
        return serviceName;
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
}
