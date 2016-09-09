package s3f.framework.register;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import s3f.Application;
import s3f.framework.amqp.system.publisher.SystemQueuePublisher;
import s3f.framework.events.S3FEvent;
import s3f.framework.lifecycle.LifeCycle;
import s3f.framework.port.ServletConfig;

@Service
public class RegistrationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationService.class);
    @Autowired
    private LifeCycle lifeCycle;


    @Value("${s3f.domain}")
    private String domain;
    @Value("${s3f.amqp.system.key}")
    private String amqpKey;

    private final RegisterInfoFactory registerInfoFactory;
    private final SystemQueuePublisher systemQueuePublisher;
    private final ServletConfig servletConfig;

    @Autowired
    public RegistrationService(RegisterInfoFactory registerInfoFactory, SystemQueuePublisher systemQueuePublisher, ServletConfig servletConfig) {
        this.registerInfoFactory = registerInfoFactory;
        this.systemQueuePublisher = systemQueuePublisher;
        this.servletConfig = servletConfig;
    }

    public void register() throws Exception {
        LOGGER.info("register");
        RegisterInfo registerInfo = registerInfoFactory.build(Application.serviceName, "Application", "" + servletConfig.getAvailableTcpPort(), lifeCycle.getKey());
        final S3FEvent s3FEvent = new S3FEvent("", "", domain, "register.service", Application.serviceName, registerInfo);
        LOGGER.info(s3FEvent.toJson());
        systemQueuePublisher.basicPublish(amqpKey, s3FEvent);
    }

    public void deregister() throws Exception {
        LOGGER.info("deregister");
        RegisterInfo registerInfo = registerInfoFactory.build(Application.serviceName, "Application", "" + servletConfig.getAvailableTcpPort(), lifeCycle.getKey());
        final S3FEvent s3FEvent = new S3FEvent("", "", domain, "deregister.service", Application.serviceName, registerInfo);
        LOGGER.info(s3FEvent.toJson());
        systemQueuePublisher.basicPublish(amqpKey, s3FEvent);
    }
}
