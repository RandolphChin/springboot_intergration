package com.shaphar.mapper.jpa;

import com.shaphar.domain.jpa.TestUsersJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;


public interface TestUsersJpaMapper extends JpaRepository<TestUsersJpa,BigDecimal> {
    TestUsersJpa getOne(BigDecimal userId);
    List<TestUsersJpa> findAll();
}
