package com.example.token.api;

import com.alibaba.fastjson.JSONObject;
import com.example.token.annotation.AuthToken;
import com.example.token.model.ResponseTemplate;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/api/")
public interface IUserController {

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ApiOperation("用户登录接口")
    public ResponseTemplate login(@RequestBody(required = false) JSONObject userInfo);



    @ApiOperation("测试token接口")
    @RequestMapping(value = "test", method = RequestMethod.GET)
    @AuthToken
    public ResponseTemplate test();
}


