package com.shaphar.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shaphar.base.DataBaseSource;
import com.shaphar.base.ResultResponseInfo;
import com.shaphar.config.db.DynamicDataSource;
import com.shaphar.mapper.PfUsersMapper;
import com.shaphar.domain.PfUsers;

import com.shaphar.service.UserService;
import com.shaphar.service.ehcache.EhCacheUserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private EhCacheUserService ehCacheuserService;


    /**
     *   restful
     * @return
     */
    @ApiOperation(value = "获取用户列表",notes = "获取所有用户信息")
    @RequestMapping(value = "/api/user",method = RequestMethod.GET)
    public ResultResponseInfo<PfUsers> listPfUsers() {
        DynamicDataSource.setDatasource(DataBaseSource.ERP_MOBILE.getDbName());
        PageHelper.startPage(1, 3);
        List<PfUsers> list = userService.listPfUsers();
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
    @ApiOperation(value = "获取单个用户信息",notes = "获取某个用户信息")
    @ApiImplicitParam(name = "userId",value = "用户ID",required = true,dataType = "Long")
    @RequestMapping(value = "/api/user/{userId}",method = RequestMethod.GET)
    public ResultResponseInfo selectPfUsersById(@PathVariable("userId") BigDecimal userId) {
        DynamicDataSource.setDatasource(DataBaseSource.ERP_MOBILE.getDbName());
        ResultResponseInfo result = ResultResponseInfo.build();
        PfUsers pfUsers = userService.selectPfUsersById(userId);
        result.setData(pfUsers);
        return result;
    }


    /**
     *   Ehcache   可以通过 console 查看是否打印了 SQL
     * @param userId
     * @return
     */
    @ApiOperation(value = "获取单个用户信息(ehcache)",notes = "获取某个用户信息(ehcache)")
    @ApiImplicitParam(name = "userId",value = "用户ID",required = true,dataType = "Long")
    @RequestMapping(value = "/api/ehcache/user/{userId}",method = RequestMethod.GET)
    public ResultResponseInfo selectPfUsersByIdEhCache(@PathVariable("userId") BigDecimal userId) {
        DynamicDataSource.setDatasource(DataBaseSource.ERP_MOBILE.getDbName());
        ResultResponseInfo result = ResultResponseInfo.build();
        PfUsers pfUsers = ehCacheuserService.selectPfUsersById(userId);
        result.setData(pfUsers);
        return result;
    }


}
