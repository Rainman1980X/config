package s3f.framework.register;

import org.junit.Test;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RegisterInfoFactoryTest {
    private final String serviceName = "s3f-ka-upload";
    private final String serviceClass = "serviceClass";
    private final String port = "30100";
    private final String lifecycle = "";

    @Test
    public void build() throws UnknownHostException {
        RegisterInfoFactory registerInfoFactory = new RegisterInfoFactory();

        RegisterInfo registerInfo = registerInfoFactory.build(serviceName, serviceClass, port, lifecycle);

        assertThat(registerInfo, is(registerInfo()));
    }

    private RegisterInfo registerInfo() throws UnknownHostException {
        return new RegisterInfo(
                InetAddress.getLocalHost().getHostName(),
                ManagementFactory.getRuntimeMXBean().getName().split("@")[0],
                serviceName,
                serviceClass,
                port,
                lifecycle
        );
    }
}
