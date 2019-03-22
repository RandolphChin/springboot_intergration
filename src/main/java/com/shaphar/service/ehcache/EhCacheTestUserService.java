package com.shaphar.service.ehcache;

import com.shaphar.domain.PfUsers;
import com.shaphar.domain.TestUsers;
import com.shaphar.mapper.PfUsersMapper;
import com.shaphar.mapper.TestUsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class EhCacheTestUserService {
    @Autowired
    private TestUsersMapper testUsersMapper;

    @Cacheable(value="sit_user",key="sit_user_all")
    public List<TestUsers> listPfUsers() {
        return testUsersMapper.listUser();
    }

    @Cacheable(value="sit_user",key="'sit_user_'+#userId")
    public TestUsers selectPfUsersById(BigDecimal userId){
        return testUsersMapper.selectByPrimaryKey(userId);
    }
}
