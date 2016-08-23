package s3f.framework.register;

import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
public class RegisterInfoFactory {
    public RegisterInfo build(String serviceName, String serviceClass, String port, String lifecycle) throws UnknownHostException {
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
