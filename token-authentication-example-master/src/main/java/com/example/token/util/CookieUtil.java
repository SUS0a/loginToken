package com.example.token.util;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Component
public class CookieUtil {

    /**
     * 设置cookie
     *
     * @param name   cookie名字
     * @param value  cookie值
     * @param maxAge cookie生命周期  以秒为单位
     */
    public static void createCookie(String name, String value, Boolean secure, Integer maxAge, String domain) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse httpServletResponse = attributes.getResponse();
        Cookie cookie = new Cookie(name, value);
        cookie.setSecure(secure);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);
        cookie.setDomain(domain); //例子: www.zjut.edu.cn 和 bbs.zjut.edu.cn
        cookie.setPath("/");
        httpServletResponse.addCookie(cookie);
    }

    /**
     * 根据名字获取cookie
     *
     * @param name cookie名字
     * @return
     */
    public static Cookie getCookieByName(String name) {
        Map<String, Cookie> cookieMap = ReadCookieMap();
        if (cookieMap.containsKey(name)) {
            Cookie cookie = (Cookie) cookieMap.get(name);
            return cookie;
        } else {
            return null;
        }
    }

    public static void clearCookie(String name) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse httpServletResponse = attributes.getResponse();
        Cookie cookie = new Cookie(name, null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        cookie.setDomain("localhost");
        httpServletResponse.addCookie(cookie);
    }

    /**
     * 将cookie封装到Map里面
     *
     * @return
     */
    private static Map<String, Cookie> ReadCookieMap() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest httpServletRequest = attributes.getRequest();

        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        Cookie[] cookies = httpServletRequest.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }

}
