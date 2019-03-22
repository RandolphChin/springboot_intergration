package com.shaphar.config;

import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TomcatConfig {
    @Bean
    public ConfigurableServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addConnectorCustomizers(
                connector -> {
                    Http11NioProtocol protocol =
                            (Http11NioProtocol) connector.getProtocolHandler();
                    protocol.setMaxThreads(800); // tomcat 并发量设置
                    protocol.setPort(8080); // tomcat 端口设置
                    protocol.setMaxConnections(800);
                    protocol.setConnectionTimeout(8000); // tomcat 超时时间设置
                }
        );
        return factory;
    }
}
