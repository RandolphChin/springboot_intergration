package com.shaphar.service.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.shaphar.config.MQConfig;
import com.shaphar.domain.TestUsers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MQSender {

	@Autowired
	private AmqpTemplate amqpTemplate ;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void sendTestUserMessage(TestUsers testUsers) {
		String testUserMsg = JSON.toJSONString(testUsers);
		log.info("........send testUserStr: "+testUserMsg+" to rabbitmq");
		amqpTemplate.convertAndSend(MQConfig.TESTUSER_QUEUE, testUserMsg);
	}


}
