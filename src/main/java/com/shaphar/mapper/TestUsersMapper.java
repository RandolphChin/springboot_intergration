package com.shaphar.mapper;

import com.shaphar.domain.PfUsers;
import com.shaphar.domain.TestUsers;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface TestUsersMapper {


    List<TestUsers> listUser();

    TestUsers selectByPrimaryKey(BigDecimal userId);

    void addTestUser(TestUsers testUsers);
}