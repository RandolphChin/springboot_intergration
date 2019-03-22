package com.shaphar.acturator;

import io.lettuce.core.ClientOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.AbstractReactiveHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.redis.RedisHealthIndicator;
import org.springframework.boot.actuate.redis.RedisReactiveHealthIndicator;
import org.springframework.boot.autoconfigure.data.redis.LettuceClientConfigurationBuilderCustomizer;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Component
public class RedisMonitor extends AbstractHealthIndicator {

    @Autowired
    private Environment environment;

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
       // 连接redis服务器
        Jedis jedis = new Jedis(environment.getProperty("redis.host"),Integer.valueOf(environment.getProperty("redis.port")));
//        测试redis服务器是否在运行
       // System.out.println(jedis.info());
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("host",jedis.getClient().getHost());
        map.put("port",jedis.getClient().getPort());
        map.put("db",jedis.getClient().getDB());
        builder.up().withDetail("redisServer", map);
    }
}
