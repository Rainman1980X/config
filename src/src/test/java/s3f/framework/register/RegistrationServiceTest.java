package s3f.framework.register;

import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import s3f.framework.amqp.system.publisher.SystemQueuePublisher;
import s3f.framework.events.S3FEvent;
import s3f.framework.lifecycle.LifeCycle;
import s3f.framework.config.ServletConfig;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class RegistrationServiceTest {
    @Test
    public void register() throws Exception {
        SystemQueuePublisher systemQueuePublisher = mock(SystemQueuePublisher.class);
        RegistrationService registrationService = new RegistrationService(new RegisterInfoFactory(), systemQueuePublisher, new ServletConfig());
        ReflectionTestUtils.setField(registrationService, "lifeCycle", new LifeCycle("local"));
        ReflectionTestUtils.setField(registrationService, "amqpKey", "E");

        registrationService.register();

        verify(systemQueuePublisher).basicPublish(eq("E"), any(S3FEvent.class));
    }
}
