package com.shaphar.mapper.mongodb;

import com.shaphar.domain.mongodb.TestUsersMongo;

import java.math.BigDecimal;
import java.util.List;


public interface TestUserMongoMapper {

    TestUsersMongo findTestUserMongoById(BigDecimal userId);
    void saveTestUsersMongo(TestUsersMongo testUsersMongo);
    List<TestUsersMongo> listTestUsersMongo();
}
