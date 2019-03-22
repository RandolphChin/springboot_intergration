package com.shaphar.service;

import com.github.pagehelper.PageHelper;
import com.shaphar.domain.PfUsers;
import com.shaphar.mapper.PfUsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private PfUsersMapper pfUsersMapper;

    public List<PfUsers> listPfUsers() {
        return pfUsersMapper.listUser();
    }


    public PfUsers selectPfUsersById(BigDecimal userId){
        return pfUsersMapper.selectByPrimaryKey(userId);
    }
}
