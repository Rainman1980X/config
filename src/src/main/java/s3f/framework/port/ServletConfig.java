package s3f.framework.port;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.util.SocketUtils;

@Controller
public class ServletConfig {
    @Value("${s3f.server.port.range.min}")
    private int s3fServerPortRangeMin;
    @Value("${s3f.server.port.range.max}")
    private int s3fServerPortRangeMax;

    public int getAvailableTcpPort() {
        return availableTcpPort;
    }

    private int availableTcpPort;

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return (container -> {
            availableTcpPort = SocketUtils.findAvailableTcpPort(s3fServerPortRangeMin, s3fServerPortRangeMax);
            container.setPort(availableTcpPort);
        });
    }
}
