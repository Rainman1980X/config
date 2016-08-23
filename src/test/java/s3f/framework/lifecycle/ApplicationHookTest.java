package s3f.framework.lifecycle;

import org.junit.Before;
import org.junit.Test;
import s3f.framework.register.RegistrationService;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ApplicationHookTest {
    private RegistrationService registrationService;

    @Before
    public void setup() {
        registrationService = mock(RegistrationService.class);
    }

    @Test
    public void startup() throws Exception {
        new ApplicationHook(registrationService).startup();

        verify(registrationService).register();
    }

    @Test
    public void shutdown() throws Exception {
        new ApplicationHook(registrationService).shutdown();

        verify(registrationService).deregister();
    }
}
