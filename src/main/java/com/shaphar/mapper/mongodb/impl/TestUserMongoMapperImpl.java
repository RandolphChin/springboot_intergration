package com.shaphar.mapper.mongodb.impl;

import com.shaphar.domain.mongodb.TestUsersMongo;
import com.shaphar.mapper.mongodb.TestUserMongoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class TestUserMongoMapperImpl implements TestUserMongoMapper {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public TestUsersMongo findTestUserMongoById(BigDecimal userId) {
        Query query = new Query(Criteria.where("userId").is(userId));
        TestUsersMongo testUsersMongo = mongoTemplate.findOne(query,TestUsersMongo.class);
        return testUsersMongo;
    }

    @Override
    public void saveTestUsersMongo(TestUsersMongo testUsersMongo) {
        mongoTemplate.save(testUsersMongo);
    }

    @Override
    public List<TestUsersMongo> listTestUsersMongo() {
        return mongoTemplate.findAll(TestUsersMongo.class);
    }

}
