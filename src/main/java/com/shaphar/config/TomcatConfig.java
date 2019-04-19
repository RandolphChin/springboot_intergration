package com.shaphar.config;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TomcatConfig {
/*
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
    */

    @Bean
    public ConfigurableServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();

         // method one
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

/*
        // method two
        factory.addConnectorCustomizers(new TomcatConnectorCustomizer(){

            @Override
            public void customize(Connector connector) {
                connector.setPort(9900);
            }
        });
        */
        return factory;
    }

/*  method three

    @Bean
    public ConfigurableServletWebServerFactory webServerFactory() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
       // factory.addAdditionalTomcatConnectors(createTomcatConnectors());
        factory.addAdditionalTomcatConnectors(createTomcatConnectors());
        return factory;
    }
    private Connector createTomcatConnectors(){
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
        protocol.setMaxThreads(800); // tomcat 并发量设置
        protocol.setPort(9000); // tomcat 端口设置
        protocol.setMaxConnections(800);
        protocol.setConnectionTimeout(8000); // tomcat 超时时间设置
        connector.setSecure(false);
        connector.setPort(9999);
        return  connector;
    }
*/

}
