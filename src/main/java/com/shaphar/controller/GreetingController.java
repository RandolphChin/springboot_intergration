package com.shaphar.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.shaphar.base.*;
import com.shaphar.config.db.DynamicDataSource;
import com.shaphar.domain.PfUsers;
import com.shaphar.domain.TestUsers;
import com.shaphar.domain.elasticsearch.Es;
import com.shaphar.domain.jpa.TestUsersJpa;
import com.shaphar.domain.mongodb.TestUsersMongo;
import com.shaphar.service.TestUserService;
import com.shaphar.service.ehcache.EhCacheTestUserService;
import com.shaphar.service.elasticsearch.ElasticSearchService;
import com.shaphar.service.jpa.JpaTestUserService;
import com.shaphar.service.memcached.MemcachedTestUserService;
import com.shaphar.service.mongodb.MongoTestUserService;
import com.shaphar.service.rabbitmq.MQSender;
import com.shaphar.service.redis.RedisTestUserService;
import com.shaphar.util.SnowflakeIdWorker;
import com.shaphar.vo.UserVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
//@RestController = @Controller + @ResponseBody
@Controller
@Slf4j
public class GreetingController {
    @Autowired
    private TestUserService testUserService;
    @Autowired
    private EhCacheTestUserService ehCacheTestUserService;
    @Autowired
    private RedisTestUserService redisTestUserService;
    @Autowired
    private JpaTestUserService jpaTestUserService;
    @Autowired
    private MemcachedTestUserService memcachedTestUserService;
    @Autowired
    private MongoTestUserService mongoTestUserService;
    @Autowired
    private MQSender mqSender;
    @Autowired
    BulkProcessor bulkProcessor;
    @Autowired
    ElasticSearchService elasticSearchService;

    /**
     *   restful
     * @return
     */
    @ApiOperation(value = "获取 SIT 用户列表",notes = "获取 SIT 所有用户信息")
    @ResponseBody
    @RequestMapping(value = "/api/sit/user",method = RequestMethod.GET)
    public ResultResponseInfo<PfUsers> listPfUsers() {
        DynamicDataSource.setDatasource(DataBaseSource.ERP_SIT.getDbName());
        PageHelper.startPage(1, 9);
        List<TestUsers> list = testUserService.listPfUsers();
        // 分页时，实际返回的结果list类型是Page<E>，如果想取出分页信息，需要强制转换为Page<E>
        long count = ((Page) list).getTotal();

        ResultResponseInfo result = ResultResponseInfo.build();
        result.setData(list);
        result.setCount(count);
        return result;
    }

    /**
     *   restful
     * @param userId
     * @return
     */
    @ApiOperation(value = "获取 SIT 单个用户信息",notes = "获取 SIT 某个用户信息")
    @ApiImplicitParam(name = "userId",value = "用户ID",required = true,dataType = "Long")
    @ResponseBody
    @RequestMapping(value = "/api/sit/user/{userId}",method = RequestMethod.GET)
    public ResultResponseInfo selectPfUsersById(@PathVariable("userId") BigDecimal userId) {
        DynamicDataSource.setDatasource(DataBaseSource.ERP_SIT.getDbName());
        ResultResponseInfo result = ResultResponseInfo.build();
        TestUsers testUsers = testUserService.selectPfUsersById(userId);
        result.setData(testUsers);
        return result;
    }


    /**
     *   restful   redis
     * @param userId
     * @return
     */
    @ApiOperation(value = "Redis获取 SIT 单个用户信息",notes = "Redis获取 SIT 某个用户信息")
    @ApiImplicitParam(name = "userId",value = "用户ID",required = true,dataType = "Long")
    @ResponseBody
    @RequestMapping(value = "/api/sit/redis/user/{userId}",method = RequestMethod.GET)
    public ResultResponseInfo selectPfUsersByIdRedis(@PathVariable("userId") BigDecimal userId) {
        DynamicDataSource.setDatasource(DataBaseSource.ERP_SIT.getDbName());
        ResultResponseInfo result = ResultResponseInfo.build();
        TestUsers testUsersRedis = redisTestUserService.selectPfUsersByIdRedis(userId);
        if(null == testUsersRedis){
            testUsersRedis = testUserService.selectPfUsersById(userId);
            log.debug("redis中没有获取到，通过DB查询出来");
            redisTestUserService.setTestUser(testUsersRedis);
        }
        result.setData(testUsersRedis);
        return result;
    }

    /**
     *  Restful Post method
     * @param
     * @return
     */
    @ApiOperation(httpMethod = "POST" ,value = "新增一个 SIT 用户",notes = "新增一个 SIT 用户")
    @ApiParam(name = "测试用户",value = "测试用户")
    @ResponseBody
    @RequestMapping(value = "/api/sit/addUser",method = RequestMethod.POST)
    public ResultResponseInfo addTestUser(@RequestBody UserVO userVO){
        DynamicDataSource.setDatasource(DataBaseSource.ERP_SIT.getDbName());
        ResultResponseInfo result = ResultResponseInfo.build();
        TestUsers testUsers = new TestUsers();
        try {
            BeanUtils.copyProperties(userVO, testUsers);
            // snowflaker 生成唯一ID
            Long systemId = SnowflakeIdWorker.getSystemId(0, 0);
            testUsers.setUserId(new BigDecimal(systemId));
            testUserService.addPfUsers(testUsers);
        }catch (Exception e){
            result.withError(ResultStatus.EXCEPTION.getCode(),e.getMessage());
        }
        return result;
    }

    /**
     *  Ehcache
     * @param userId
     * @return
     */
    @ApiOperation(value = "获取单个用户信息(ehcache)",notes = "获取某个用户信息(ehcache)")
    @ApiImplicitParam(name = "userId",value = "用户ID",required = true,dataType = "Long")
    @ResponseBody
    @RequestMapping(value = "/api/sit/ehcache/user/{userId}",method = RequestMethod.GET)
    public ResultResponseInfo selectPfUsersByIdEhCache(@PathVariable("userId") BigDecimal userId) {
        DynamicDataSource.setDatasource(DataBaseSource.ERP_SIT.getDbName());
        ResultResponseInfo result = ResultResponseInfo.build();
        TestUsers TestUsers = ehCacheTestUserService.selectPfUsersById(userId);
        result.setData(TestUsers);
        return result;
    }

    /**
     *    websocket
     * @param userId
     * @return
     * @throws Exception
     */
    @MessageMapping("/user/websocket/{userId}")//浏览器发送请求通过@messageMapping 映射 这个地址。
    @SendTo("/topic/getResponse")//服务器端有消息时,会订阅@SendTo 中的路径的浏览器发送消息。
    public ResultResponseInfo say(@PathVariable("userId") BigDecimal userId) throws Exception {
        ResultResponseInfo result = ResultResponseInfo.build();
        TestUsers testUser = testUserService.selectPfUsersById(userId);
        result.setData(testUser);
        return result;
    }

    /**
     *  Thymeleaf
     * @param model
     * @return
     */
    @ApiOperation(value = "Thymeleaf 列出 SIT 用户列表信息",notes = "Thymeleaf 列出 SIT 用户列表信息")
    @RequestMapping(value = "/sit/thymeleaf/user/",method = RequestMethod.GET)
    public String listPfUsersByIdThymleaf(ModelMap model) {
        DynamicDataSource.setDatasource(DataBaseSource.ERP_SIT.getDbName());
        PageHelper.startPage(1, 4);
        List<TestUsers> userList = testUserService.listPfUsers();
        model.addAttribute("userList", userList);
        return "list";
    }

    /**
     *  JPA
     * @param userId
     * @return
     */
    @ApiOperation(value = "获取 SIT 单个用户信息(JPA)",notes = "获取 SIT 某个用户信息(JPA)")
    @ApiImplicitParam(name = "userId",value = "用户ID",required = true,dataType = "Long")
    @ResponseBody
    @RequestMapping(value = "/api/sit/jpa/user/{userId}",method = RequestMethod.GET)
    public ResultResponseInfo selectPfUsersByIdByJpa(@PathVariable("userId") BigDecimal userId) {
        DynamicDataSource.setDatasource(DataBaseSource.ERP_SIT.getDbName());
        ResultResponseInfo result = ResultResponseInfo.build();
        TestUsersJpa testUsersJpa = jpaTestUserService.getOneById(userId);
        result.setData(testUsersJpa);
        return result;
    }

    /**
     *     Memcached
     * @param userId
     * @return
     */
    @ApiOperation(value = "Memcached 获取 SIT 单个用户信息",notes = "Memcached 获取 SIT 某个用户信息")
    @ApiImplicitParam(name = "userId",value = "用户ID",required = true,dataType = "Long")
    @ResponseBody
    @RequestMapping(value = "/api/sit/memcached/user/{userId}",method = RequestMethod.GET)
    public ResultResponseInfo selectPfUsersByIdMemcached(@PathVariable("userId") BigDecimal userId) {
        DynamicDataSource.setDatasource(DataBaseSource.ERP_SIT.getDbName());
        ResultResponseInfo result = ResultResponseInfo.build();
        TestUsers testUsersRedis = memcachedTestUserService.selectUsersByIdMemcached(userId);
        if(null == testUsersRedis){
            testUsersRedis = testUserService.selectPfUsersById(userId);
            log.debug("redis中没有获取到，通过DB查询出来");
            memcachedTestUserService.setTestUsersByMemcached(testUsersRedis);
        }
        result.setData(testUsersRedis);
        return result;
    }

    /**
     *   MongoDb Get
     * @param userId
     * @return
     */
    @ApiOperation(value = "Mongodb 获取 SIT 单个用户信息",notes = "Mongodb 获取 SIT 某个用户信息")
    @ApiImplicitParam(name = "userId",value = "用户ID 示例userId 553606738327109632",required = true,dataType = "Long")
    @ResponseBody
    @RequestMapping(value = "/api/sit/mongodb/user/{userId}",method = RequestMethod.GET)
    public ResultResponseInfo selectPfUsersByIdMongo(@PathVariable("userId") BigDecimal userId) {
        DynamicDataSource.setDatasource(DataBaseSource.ERP_SIT.getDbName());
        ResultResponseInfo result = ResultResponseInfo.build();
        TestUsersMongo testUsersMongo = mongoTestUserService.findTestUserMongoById(userId);
        result.setData(testUsersMongo);
        return result;
    }

    /**
     *   MongoDb Post
     * @param userVO
     * @return
     */
    @ApiOperation(httpMethod = "POST" ,value = "Mongodb 新增一个 SIT 用户",notes = "Mongodb 新增一个 SIT 用户")
    @ApiParam(name = "MongoDb 测试用户",value = "MongoDb 测试用户")
    @ResponseBody
    @RequestMapping(value = "/api/sit/mongodb/addUser",method = RequestMethod.POST)
    public ResultResponseInfo addTestUserMongoDb(@RequestBody UserVO userVO){
        DynamicDataSource.setDatasource(DataBaseSource.ERP_SIT.getDbName());
        ResultResponseInfo result = ResultResponseInfo.build();
        TestUsersMongo testUsersMongo = new TestUsersMongo();
        try {
            BeanUtils.copyProperties(userVO, testUsersMongo);
            // snowflaker 生成唯一ID
            Long systemId = SnowflakeIdWorker.getSystemId(0, 0);
            testUsersMongo.setUserId(new BigDecimal(systemId));
            mongoTestUserService.saveTestUsersMongo(testUsersMongo);
        }catch (Exception e){
            result.withError(ResultStatus.EXCEPTION.getCode(),e.getMessage());
        }
        return result;
    }

    /**
     *  MongoDb Get
     * @return
     */
    @ApiOperation(value = "Mongodb 获取 SIT 用户列表",notes = "Mongodb 获取 SIT 用户列表")
    @ResponseBody
    @RequestMapping(value = "/api/sit/mongodb/user/",method = RequestMethod.GET)
    public ResultResponseInfo selectTestUsersListByIdMongo() {
        DynamicDataSource.setDatasource(DataBaseSource.ERP_SIT.getDbName());
        ResultResponseInfo result = ResultResponseInfo.build();
        List<TestUsersMongo> testUsersMongoList = mongoTestUserService.listTestUsersMongo();
        result.setData(testUsersMongoList);
        return result;
    }

    /**
     *   RabbitMQ
     * @param userVO
     * @return
     */
    @ApiOperation(httpMethod = "POST" ,value = "RabbitMQ 新增一个 SIT 用户",notes = "RabbitMQ新增一个 SIT 用户")
    @ApiParam(name = "RabbitMQ add user",value = "RabbitMQ add user")
    @ResponseBody
    @RequestMapping(value = "/api/sit/rabbitMq/addUser",method = RequestMethod.POST)
    public ResultResponseInfo addTestUserByMQ(@RequestBody UserVO userVO){
        DynamicDataSource.setDatasource(DataBaseSource.ERP_SIT.getDbName());
        ResultResponseInfo result = ResultResponseInfo.build();
        TestUsers testUsers = new TestUsers();
        try {
            BeanUtils.copyProperties(userVO, testUsers);
            // snowflaker 生成唯一ID
            Long systemId = SnowflakeIdWorker.getSystemId(0, 0);
            testUsers.setUserId(new BigDecimal(systemId));

         //   testUserService.addPfUsers(testUsers);
            mqSender.sendTestUserMessage(testUsers);
        }catch (Exception e){
            result.withError(ResultStatus.EXCEPTION.getCode(),e.getMessage());
        }
        return result;
    }

    /**
     * elasticsearch  初始化
     * @return
     */
    @ApiOperation(value = "elasticsearch 初始化 SIT 用户列表,建立索引",notes = "elasticsearch 初始化 SIT 所有用户信息建立索引")
    @ResponseBody
    @RequestMapping(value = "/api/sit/elasticsearch/createUserESIndex",method = RequestMethod.GET)
    public ResultResponseInfo<PfUsers> createUserESIndex() {
        DynamicDataSource.setDatasource(DataBaseSource.ERP_SIT.getDbName());
        PageHelper.startPage(1, 200);
        List<TestUsers> list = testUserService.listPfUsers();
        // 分页时，实际返回的结果list类型是Page<E>，如果想取出分页信息，需要强制转换为Page<E>
        // long count = ((Page) list).getTotal();
        ResultResponseInfo result = ResultResponseInfo.build();
        for(TestUsers t : list){
            String jsonStr = JSON.toJSONString(t, SerializerFeature.WriteDateUseDateFormat);
            bulkProcessor.add(new IndexRequest("search_index",
                    "search_index", t.getUserId()+"")
                    .source(jsonStr, XContentType.JSON));
        }
        return result;
    }

    /**
     *  elasticsearch Get
     * @param userId
     * @return
     */
    @ApiOperation(value = "ElasticSearch 获取 SIT 单个用户信息",notes = "ElasticSearch 获取 SIT 某个用户信息")
    @ApiImplicitParam(name = "userId",value = "用户ID 示例872",required = true,dataType = "Long")
    @ResponseBody
    @RequestMapping(value = "/api/sit/elasticsearch/user/{userId}",method = RequestMethod.GET)
    public ResultResponseInfo selectTestUsersByIdES(@PathVariable("userId") BigDecimal userId) {
        DynamicDataSource.setDatasource(DataBaseSource.ERP_SIT.getDbName());
        ResultResponseInfo result = ResultResponseInfo.build();
        Es es = new Es("search_index","search_index");
        String testUsersStr = elasticSearchService.selectfromEsByid(es,userId);
        TestUsers testUsers = JSONObject.parseObject(testUsersStr,TestUsers.class);
        result.setData(testUsers);
        return result;
    }

    /**
     * ElasticSearch Get
     * @param fieldName
     * @param fieldVal
     * @return
     */
    @ApiOperation(value = "ElasticSearch 条件检索获取 SIT 用户信息",notes = "ElasticSearch 条件检索获取 SIT 用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "检索字段名", value = "检索字段名，如 userName", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "检索字段内容", value = "检索字段内容，如 jackie chan", required = true, paramType = "query", dataType = "String")
    })
    @ResponseBody
    @RequestMapping(value = "/api/sit/elasticsearch/user/{fieldName}/{fieldVal}",method = RequestMethod.GET)
    public ResultResponseInfo selectTestUsersByIdES(@PathVariable("fieldName") String fieldName,@PathVariable("fieldVal") String fieldVal) {
        DynamicDataSource.setDatasource(DataBaseSource.ERP_SIT.getDbName());
        ResultResponseInfo result = ResultResponseInfo.build();
        Es es = new Es("search_index","search_index");
        SearchHits searchHits = elasticSearchService.selectfromEsByField(es,fieldName,fieldVal);
        List<String> list = new ArrayList<String>();
        long totalHits = searchHits.getTotalHits();
        for(SearchHit sh : searchHits){
            // Map<String,Object> map= sh.getSourceAsMap();
            String str =sh.getSourceAsString();
            list.add(str);
        }
        result.setData(list);
        result.setCount(totalHits);
        return result;
    }

    /**
     *  ElasticSearch Post
     * @param userVO
     * @return
     */
    @ApiOperation(httpMethod = "POST" ,value = "ElasticSearch 新增一个 SIT 用户",notes = "ElasticSearch 新增一个 SIT 用户")
    @ApiParam(name = "ElasticSearch add user",value = "ElasticSearch add user")
    @ResponseBody
    @RequestMapping(value = "/api/sit/elasticSearch/addUser",method = RequestMethod.POST)
    public ResultResponseInfo addTestUserByES(@RequestBody UserVO userVO){
        DynamicDataSource.setDatasource(DataBaseSource.ERP_SIT.getDbName());
        ResultResponseInfo result = ResultResponseInfo.build();
        TestUsers testUsers = new TestUsers();
        try {
            BeanUtils.copyProperties(userVO, testUsers);
            // snowflaker 生成唯一ID
            Long systemId = SnowflakeIdWorker.getSystemId(0, 0);
            testUsers.setUserId(new BigDecimal(systemId));
            mqSender.sendTestUserMessage(testUsers);
            String jsonStr = JSON.toJSONString(testUsers, SerializerFeature.WriteDateUseDateFormat);
            bulkProcessor.add(new IndexRequest("search_index",
                    "search_index", systemId+"")
                    .source(jsonStr, XContentType.JSON));
        }catch (Exception e){
            result.withError(ResultStatus.EXCEPTION.getCode(),e.getMessage());
        }
        return result;
    }

    /**
     * ElasticSearch DELETE
     * @param userId
     * @return
     */
    @ApiOperation(value = "ElasticSearch 删除 SIT 单个用户信息",notes = "ElasticSearch 删除 SIT 某个用户信息")
    @ApiImplicitParam(name = "userId",value = "用户ID 示例872",required = true,dataType = "Long")
    @ResponseBody
    @RequestMapping(value = "/api/sit/elasticsearch/removeUser/{userId}",method = RequestMethod.DELETE)
    public ResultResponseInfo removeTestUsersById(@PathVariable("userId") BigDecimal userId) {
        DynamicDataSource.setDatasource(DataBaseSource.ERP_SIT.getDbName());
        ResultResponseInfo result = ResultResponseInfo.build();
        Es es = new Es("search_index", "search_index");
        String testUsersStr = elasticSearchService.selectfromEsByid(es, userId);
        if (null == testUsersStr) {
            result.withError(ResultStatus.FAILD.getCode(), "userId " +ResultStatus.FAILD.getMessage());
            return result;
        }
        jpaTestUserService.deleteById(userId);
        DeleteResponse response = elasticSearchService.deleteById(es, userId);
        if (response.getResult().getLowercase().equals(ResultStatus.NOT_FOUND.getMessage())) {
            result.withError(ResultStatus.NOT_FOUND.getCode(), ResultStatus.NOT_FOUND.getMessage());
            return result;
        }
        return result;
    }

    //*****************************************session 共享测试 BEGIN ***************************************************************
    @RequestMapping(value = "/setUrlToSession", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> firstResp (HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        request.getSession().setAttribute("request Url", request.getRequestURL());
        map.put("request Url", request.getRequestURL());
        return map;
    }

    @RequestMapping(value = "/sessions", method = RequestMethod.GET)
    @ResponseBody
    public Object sessions (HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        map.put("sessionId", request.getSession().getId());
        map.put("message", request.getSession().getAttribute("request Url"));
        return map;
    }
    //****************************************session 共享测试 END ****************************************************************
}
