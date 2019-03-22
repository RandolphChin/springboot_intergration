package com.shaphar.service.ehcache;

import com.shaphar.domain.PfUsers;
import com.shaphar.mapper.PfUsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class EhCacheUserService {
    @Autowired
    private PfUsersMapper pfUsersMapper;

    @Cacheable(value="user",key="'user_all")
    public List<PfUsers> listPfUsers() {
        return pfUsersMapper.listUser();
    }

    @Cacheable(value="user",key="'user_'+#userId")
    public PfUsers selectPfUsersById(BigDecimal userId){
        return pfUsersMapper.selectByPrimaryKey(userId);
    }
}
