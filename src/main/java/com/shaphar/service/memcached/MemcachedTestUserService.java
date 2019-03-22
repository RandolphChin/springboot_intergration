package com.shaphar.service.memcached;

import com.shaphar.base.KeyPrefix;
import com.shaphar.config.MemcachedClientConfig;
import com.shaphar.domain.TestUsers;
import net.spy.memcached.MemcachedClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Service
public class MemcachedTestUserService {
    @Autowired
    private MemcachedClientConfig memcachedRunner;
    @Autowired
    private Environment environment;

    public TestUsers selectUsersByIdMemcached(BigDecimal userId){
        MemcachedClient memcachedClient = getMemcachedClient();
        TestUsers testUsers = (TestUsers)memcachedClient.get(KeyPrefix.SIT_PREFIX.getMessage()+""+userId+"");
        return  testUsers;
    }
    public void setTestUsersByMemcached(TestUsers testUsers){
        MemcachedClient memcachedClient = getMemcachedClient();
        memcachedClient.set(KeyPrefix.SIT_PREFIX.getMessage()+""+testUsers.getUserId()+"",Integer.valueOf(environment.getProperty("memcached.exp")),testUsers);
    }

    public MemcachedClient getMemcachedClient(){
        MemcachedClient memcachedClient = memcachedRunner.getClient();
        return  memcachedClient;
    }
}
