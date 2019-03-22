package com.shaphar.service.redis;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shaphar.base.KeyPrefix;
import com.shaphar.domain.TestUsers;
import com.shaphar.mapper.TestUsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class RedisTestUserService {

        @Autowired
        private StringRedisTemplate stringRedisTemplate;

        @Autowired
        private TestUsersMapper testUsersMapper;

        public TestUsers selectPfUsersByIdRedis(BigDecimal userId){

              String testUserStr = (String)stringRedisTemplate.opsForValue().get(KeyPrefix.SIT_PREFIX.getMessage()+""+userId+"");
            if(null != testUserStr){
                TestUsers testUsers = JSON.parseObject(testUserStr,TestUsers.class);
                return testUsers;
            }
            return  null;
        }

        public void setTestUser(TestUsers testUsers){
            String result = JSON.toJSONString(testUsers);
            stringRedisTemplate.opsForValue().set(KeyPrefix.SIT_PREFIX.getMessage()+""+testUsers.getUserId()+"",result);
        }
}
