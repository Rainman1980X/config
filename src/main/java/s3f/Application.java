package s3f;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import s3f.framework.lifecycle.LifeCycle;
import s3f.framework.lifecycle.LifecycleUrlDictionary;


@SpringBootApplication
public class Application {
    private static String[] args;

    /**
     * lifecycle (i.a. -develop, -test, -stage, -production)
     *
     * @see https://s3f.sintec.de/_layouts/15/start.aspx#/SitePages/Application-Lifecycle.aspx
     */
    public static void main(String[] args) {
        Application.args = args;
        new LifecycleUrlDictionary().check(args);
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
}
