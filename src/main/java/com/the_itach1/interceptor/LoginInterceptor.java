package com.the_itach1.interceptor;


import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//相当于一张网，拦截权限不够的访问。
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //判断是否是登陆状态，不是就返回到登录界面
        if(request.getSession().getAttribute("user")==null)
        {
            response.sendRedirect("/admin");
            return false;
        }
        return true;
    }
}
