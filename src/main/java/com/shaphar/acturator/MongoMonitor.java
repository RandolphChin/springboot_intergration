package com.shaphar.acturator;

import com.mongodb.MongoClient;

import com.shaphar.base.DataBaseSource;
import com.shaphar.config.db.DynamicDataSource;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;


@Component
public class MongoMonitor extends AbstractHealthIndicator {

    private final MongoClient client;

    public MongoMonitor(MongoClient client) {
        this.client = client;
    }
    @Autowired
    private DynamicDataSource dataSource;

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        try {

            Document document = new Document();

            document.put("buildInfo", 1);
            Document result = client.getDatabase(DataBaseSource.ERP_SIT.getDbName()).runCommand(document);
           // System.out.println(result);
        } catch (Exception e) {

            System.out.println("mongo失去响应 告警!!!!");

            throw e;
        }

        builder.up().withDetail("mongoServer", client.getAddress());

    }

}