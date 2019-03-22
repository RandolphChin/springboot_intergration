#### 技术
* restful   √
* websocket  http://localhost:8080/ws   √
* thymeleaf    √
* swagger2  http://localhost:8080/swagger-ui.html  √
* mybatis    √
* jpa        √
* cache缓存机制  ehcache   √
* memcache   /home/memcached_test/memcached  √
>启动  ./memcached -d -p 11210 -u admin
* redis     Lettuce Redis  安装目录 /home/redis_test/redis    √
>启动 /home/redis_test/redis/bin/redis-server /home/redis_test/redis/etc/redis.conf 
* session共享  @EnableRedisHttpSession  √
* elasticsearch  /home/elasticsearch_test  √
>启动  bin/elasticsearch -d    # 后台启动
* rabbitMQ   /home/rabbitmq_server-3.7.13    √
>启动 ./rabbitmq-server -detached
Web Console访问地址 http://10.1.225.228:15672/
* MongoDB    /home/mongodb-linux-x86_64-4.0.6    √
>启动 ./mongod --config /home/mongodb-linux-x86_64-4.0.6/etc/mongodb.conf
* Quartz    √
* Actuator http://localhost:8080/actuator/health     √
* security  使用Spring Security来保证Actuator Endpoints安全  √
* angular JS
* atomikos 动态数据源 √
注意事项：
>1. redis、elasticsearch、rabbitMQ、MongoDB都在10.1.225.228上装
>2. 装在admin/1qaz@WSX 用户下，不许用root(root/server)(admin/1qaz@WSX)

mongodb启动方式 ./mongod --config /home/mongodb-linux-x86_64-4.0.6/etc/mongodb.conf
#### POST接口测试数据
swagger接口中测试 POST 接口数据
```
{
  "admin": "string",
  "expireDate": "2019-03-05T07:46:12.078Z",
  "isGroup": "FALSE",
  "lastPasswordChangeDate": "2019-03-05T07:46:12.078Z",
  "loginInitpwd": "string",
  "loginMd5Password": "string",
  "loginName": "springboot",
  "loginNickname": "spring",
  "loginTimes": 4,
  "partyId": 28327,
  "passwordLog": "string",
  "userName": "jackie chan",
  "userStatus": "FALSE",
  "userType": "string"
}
```
#### Session共享测试 
1. 开启两个tomcat服务，端口分别为8080和9090
2. http://localhost:8080/setUrlToSession
3. http://localhost:8080/sessions
4. http://localhost:9090/sessions
步骤3和步骤4返回结果一样，则 session共享
#### 温馨提示
>测试 ehcache memcached 时需要 pom.xml 中注释掉 spring-boot-devtools，
否则会报ClassCastException
