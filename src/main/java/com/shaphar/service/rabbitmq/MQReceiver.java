package com.shaphar.service.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.shaphar.config.MQConfig;
import com.shaphar.domain.TestUsers;
import com.shaphar.mapper.TestUsersMapper;
import com.shaphar.service.redis.RedisTestUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

@Service
@Slf4j
public class MQReceiver {

	@Autowired
	private TestUsersMapper testUsersMapper;
	@Autowired
	private PlatformTransactionManager transactionManager;
	@Autowired
	private RedisTestUserService redisTestUserService;
		
		@RabbitListener(queues= MQConfig.TESTUSER_QUEUE)
		public void receive(String message) {
			log.info("........receive message:"+message+" from rabbitmq");
			TestUsers testUsers = JSON.parseObject(message,TestUsers.class);

			TransactionTemplate template=new TransactionTemplate(transactionManager);
			template.execute(new TransactionCallback() {
				@Override
				public String doInTransaction(TransactionStatus status) {
					try {
						// do something related transaction
						// 添加入库
						testUsersMapper.addTestUser(testUsers);
						// 入缓存 redis
						redisTestUserService.setTestUser(testUsers);
					} catch (Exception e) {
						log.error("........handle message:"+message+" from rabbitmq error");
						status.setRollbackOnly();
					}
					return null;
				}
			});
		}

}
