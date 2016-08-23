package s3f.framework.events;

import org.junit.Test;

public class S3FEventTest {
    @Test
    public void instantiate() {
        new S3FEvent("correlationId", "authorization", "domain", "eventName", "createdBySvc", "eventData");
    }
}