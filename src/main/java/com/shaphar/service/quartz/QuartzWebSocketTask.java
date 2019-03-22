package com.shaphar.service.quartz;

import com.github.pagehelper.PageHelper;
import com.shaphar.domain.TestUsers;
import com.shaphar.mapper.TestUsersMapper;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.List;

/**
 * @DisallowConcurrentExecution 作用：在xml配置时我们配置了concurrent为false,这个意思是是否并发执行，系统默认为true,即第一个任务还未执行完整，
 * 第二个任务如果到了执行时间，则会立马开启新线程执行任务，这样如果我们是从数据库读取信息，
 * 两次重复读取可能出现重复执行任务的情况，所以我们需要将这个值设置为false，这样第二个任务会往后推迟，
 * 只有在第一个任务执行完成后才会执行第二个任务。
 */
@DisallowConcurrentExecution
public class QuartzWebSocketTask extends QuartzJobBean{
    @Autowired
    private TestUsersMapper testUsersMapper;

    @Autowired
    private SimpMessagingTemplate template;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("WebSocket定时执行的任务开始 查询1条记录《《《《《《《《《《《《《《《《《《《《模拟向客户端发送数据");
        PageHelper.startPage(1, 1);
        List<TestUsers> list = testUsersMapper.listUser();
        template.convertAndSend("/topic/getResponse", list.get(0));
        System.out.println("----------------------data send from server" );
        System.out.println(list);
        System.out.println("WebSocket定时执行的任务结束 查询1条记录》》》》》》》》》》》》》》》》》》模拟向客户端发送数据");
    }
}
