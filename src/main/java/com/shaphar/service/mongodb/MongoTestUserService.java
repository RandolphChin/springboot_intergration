package com.shaphar.service.mongodb;

import com.shaphar.domain.mongodb.TestUsersMongo;
import com.shaphar.mapper.mongodb.TestUserMongoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class MongoTestUserService {
    @Autowired
    private TestUserMongoMapper testUserMongoMapper;

    public TestUsersMongo findTestUserMongoById(BigDecimal userId) {
        return testUserMongoMapper.findTestUserMongoById(userId);
    }

    public void   saveTestUsersMongo(TestUsersMongo testUsersMongo){
        testUserMongoMapper.saveTestUsersMongo(testUsersMongo);
    }

    public List<TestUsersMongo> listTestUsersMongo(){
        return testUserMongoMapper.listTestUsersMongo();
    }
}
