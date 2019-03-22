package com.shaphar.controller;

import com.github.pagehelper.PageHelper;
import com.shaphar.base.DataBaseSource;
import com.shaphar.config.db.DynamicDataSource;
import com.shaphar.domain.TestUsers;
import com.shaphar.service.TestUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;


@Controller
public class TestController {
    @Autowired
    private TestUserService testUserService;


}
