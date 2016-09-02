package s3f.framework.lifecycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import s3f.framework.register.RegistrationService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Configuration
public class ApplicationHook {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationHook.class);

    private final RegistrationService registrationService;

    @Autowired
    public ApplicationHook(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostConstruct
    public void startup() throws Exception {
        LOGGER.info("startup");
        registrationService.register();
    }

    @PreDestroy
    public void shutdown() throws Exception {
        LOGGER.info("shutdown");
        registrationService.deregister();
    }
}
