package com.shaphar.service;

import com.shaphar.domain.PfUsers;
import com.shaphar.domain.TestUsers;
import com.shaphar.mapper.PfUsersMapper;
import com.shaphar.mapper.TestUsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TestUserService {
    @Autowired
    private TestUsersMapper testUsersMapper;

    public List<TestUsers> listPfUsers() {
        return testUsersMapper.listUser();
    }


    public TestUsers selectPfUsersById(BigDecimal userId){
        return testUsersMapper.selectByPrimaryKey(userId);
    }

    public void addPfUsers(TestUsers testUsers){
        testUsersMapper.addTestUser(testUsers);
    }
}
