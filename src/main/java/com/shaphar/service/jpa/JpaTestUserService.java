package com.shaphar.service.jpa;

import com.shaphar.domain.jpa.TestUsersJpa;
import com.shaphar.mapper.jpa.TestUsersJpaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class JpaTestUserService {
    @Autowired
    private TestUsersJpaMapper testUsersJpaMapper;

    public TestUsersJpa getOneById(BigDecimal userId){
        return testUsersJpaMapper.getOne(userId);
    }
    public List<TestUsersJpa> findAll(){
        return testUsersJpaMapper.findAll();
    }
    public void deleteById(BigDecimal userId){
        testUsersJpaMapper.deleteById(userId);
    }
}
