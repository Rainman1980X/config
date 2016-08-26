package s3f.framework.amqp.system.configuration;

import org.junit.Test;

import static org.junit.Assert.*;

public class SystemQueueConfigurationTest {
    @Test
    public void instantiate(){
        new SystemQueueConfiguration().getHost();
    }
}