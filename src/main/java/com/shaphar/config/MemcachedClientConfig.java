package com.shaphar.config;


import lombok.extern.slf4j.Slf4j;
import net.spy.memcached.MemcachedClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import sun.tools.jar.CommandLine;

import java.io.IOException;
import java.net.InetSocketAddress;
@Component
@Slf4j
public class MemcachedClientConfig implements CommandLineRunner{
    @Autowired
    private Environment environment;

    private MemcachedClient client = null;

    @Override
    public void run(String... strings) throws Exception {
        try {
            client = new MemcachedClient(new InetSocketAddress(environment.getProperty("memcached.ip"),Integer.valueOf(environment.getProperty("memcached.port"))));
        } catch (IOException e) {
            log.error("inint MemcachedClient failed ",e);
        }
    }

    public MemcachedClient getClient() {
        return client;
    }
}
