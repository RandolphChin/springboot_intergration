package com.shaphar.config;


import com.shaphar.service.quartz.QuartzTask;
import com.shaphar.service.quartz.QuartzWebSocketTask;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class QuartzConfig {
    @Autowired
    private Environment environment;

    @Bean
    public JobDetail executeTaskDetail(){
        return JobBuilder.newJob(QuartzTask.class).withIdentity("quartzTask").storeDurably().build();
    }
    @Bean
    public Trigger uploadTaskTrigger() {
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(environment.getProperty("quartz.expression"));
        return TriggerBuilder.newTrigger().forJob(executeTaskDetail())
                .withIdentity("quartzTask")
                .withSchedule(scheduleBuilder)
                .build();
    }

    /**
     *   用以模拟从服务器端向客户端发起 websocket 请求
     * @return
     */
    @Bean
    public JobDetail executeWebsocketTaskDetail(){
        return JobBuilder.newJob(QuartzWebSocketTask.class).withIdentity("quartzWebSocketTask").storeDurably().build();
    }

    /**
     * 用以模拟从服务器端向客户端发起 websocket 请求
     * @return
     */
    @Bean
    public Trigger websocketTaskTrigger() {
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(environment.getProperty("quartz.websocket.expression"));
        return TriggerBuilder.newTrigger().forJob(executeWebsocketTaskDetail())
                .withIdentity("quartzWebSocketTask")
                .withSchedule(scheduleBuilder)
                .build();
    }
}