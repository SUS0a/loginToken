package com.example.token.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.example.token.annotation.AuthToken;
import com.example.token.api.IUserController;
import com.example.token.entity.User;
import com.example.token.kit.ConstantKit;
import com.example.token.kit.Md5TokenGenerator;
import com.example.token.mapper.UserMapper;
import com.example.token.model.ResponseTemplate;
import com.example.token.util.CookieUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author cailei.lu
 * @description
 * @date 2018/8/3
 */

@RestController
@Slf4j
public class UserController implements IUserController {

    @Autowired
    Md5TokenGenerator tokenGenerator;

    @Autowired
    UserMapper userMapper;

    @Autowired
    CookieUtil cookieUtil;

    @Override
    public ResponseTemplate login(@RequestBody(required = false) JSONObject userInfo) {

        String username = userInfo.getString("username");
        String password = userInfo.getString("password");

//        User curentUser = new User().selectOne(new EntityWrapper<User>()
//                .eq("username",username)
//                .eq("password",password)
//                .eq("del_flag",1));

        List<User> users = userMapper.selectList(new EntityWrapper<User>()
                .eq("username", username)
                .eq("password", password));
        User currentUser = users.get(0);

        JSONObject result = new JSONObject();
        if (currentUser != null) {

            Jedis jedis = new Jedis("localhost", 6379); //redis启动redis-server.exe redis.windows.conf
            String token = tokenGenerator.generate(username, password);
            jedis.set(token, "username=" + username);       //redis暂存一个username
            jedis.expire(token, ConstantKit.TOKEN_EXPIRE_TIME);
//           jedis.set(username, token);
//            jedis.expire(username, ConstantKit.TOKEN_EXPIRE_TIME);
//            Long currentTime = System.currentTimeMillis();
//            jedis.set(token + username, currentTime.toString());

            //用完关闭
            jedis.close();
            //token存入cookie
            cookieUtil.createCookie("loginToken", token , false,60,"domain");
            Cookie cookie = new Cookie("loginToken", token);
            result.put("status", "登录成功");
            result.put("token", token);
        } else {
            result.put("status", "登录失败");
        }

        return ResponseTemplate.builder().code(200)
                .message("登录成功")
                .data(result)
                .build();

    }

    @Override
    public ResponseTemplate test() {
        List<User> user = new User().selectAll();
        return ResponseTemplate.builder()
                .code(200)
                .message("Success")
                .data(user)
                .build();
    }
}
