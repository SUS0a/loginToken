package com.example.token.kit;

import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.sql.Timestamp;

/**
 * @author cailei.lu
 * @description
 * @date 2018/8/3
 */
@Component
public class Md5TokenGenerator implements TokenGenerator {

    @Override
    public String generate(String... strings) {//形参可以是1个也可以是多个，是JAVA泛型的一种体现，原理与(String[] names)基本一致
        long   timestamp = System.currentTimeMillis();//返回的是从GMT 1970年1月1日00:00:00开始到现在的毫秒数(long型)
        String tokenMeta = "";
        for (String s : strings) {
            tokenMeta = tokenMeta + s;
        }
        tokenMeta = tokenMeta + timestamp;
        String token = DigestUtils.md5DigestAsHex(tokenMeta.getBytes());
        return token;
    }
}
