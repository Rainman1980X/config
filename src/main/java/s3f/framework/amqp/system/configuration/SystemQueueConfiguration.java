package s3f.framework.amqp.system.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SystemQueueConfiguration {
    @Value("${s3f.amqp.system.host}")
    private String host;
    @Value("${s3f.amqp.system.username}")
    private String username;
    @Value("${s3f.amqp.system.password}")
    private String password;
    @Value("${s3f.amqp.system.port}")
    private String port;
    @Value("${s3f.amqp.system.virtualHost}")
    private String virtualHost;
    @Value("${s3f.amqp.system.queue}")
    private String queue;
    @Value("${s3f.amqp.system.type}")
    private String type;

    public String getHost() {
        return host;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPort() {
        return port;
    }

    public String getVirtualHost() {
        return virtualHost;
    }

    public String getQueue() {
        return queue;
    }

    public String getType() {
        return type;
    }
}
