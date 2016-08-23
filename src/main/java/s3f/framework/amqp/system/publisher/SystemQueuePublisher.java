package s3f.framework.amqp.system.publisher;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import s3f.framework.amqp.system.configuration.SystemQueueConfiguration;
import s3f.framework.events.S3FEvent;
import s3f.framework.lifecycle.LifeCycle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class SystemQueuePublisher {
    private static final Logger LOGGER = LoggerFactory.getLogger(SystemQueuePublisher.class);
    private Channel channel;
    private Connection connection;

    @Autowired
    private SystemQueueConfiguration systemQueueConfiguration;

    @Autowired
    private LifeCycle lifeCycle;

    @PostConstruct
    private void startup() throws Exception {
        LOGGER.info("startup");
        LOGGER.info(lifeCycle.getKey());
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(systemQueueConfiguration.getHost());
        factory.setPassword(systemQueueConfiguration.getPassword());
        factory.setUsername(systemQueueConfiguration.getUsername());
        factory.setVirtualHost(systemQueueConfiguration.getVirtualHost());
        connection = factory.newConnection();
        channel = connection.createChannel();
        channel.exchangeDeclare(lifeCycle.getKey(), systemQueueConfiguration.getType(), true);
        channel.queueDeclare(systemQueueConfiguration.getQueue(), true, false, false, null);
        channel.queueBind(systemQueueConfiguration.getQueue(), lifeCycle.getKey(), "E");
    }

    public void basicPublish(String routingKey, S3FEvent s3FEvent) throws Exception {
        channel.basicPublish(lifeCycle.getKey(), routingKey, null, s3FEvent.toJson().getBytes());
    }

    @PreDestroy
    private void shutdown() throws Exception {
        LOGGER.info("shutdown");
        channel.close();
        connection.close();
    }


}
