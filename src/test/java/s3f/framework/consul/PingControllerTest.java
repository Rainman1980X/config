package s3f.framework.consul;

import org.junit.Test;

import static org.junit.Assert.*;

public class PingControllerTest {
    @Test
    public void ping() {
        new PingController().ping();
    }
}