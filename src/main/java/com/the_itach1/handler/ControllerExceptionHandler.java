package com.the_itach1.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

//拦截异常处理
@ControllerAdvice
public class ControllerExceptionHandler {

    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    //ModelAndView这个对象可以控制返回一个页面，和控制输出到前端的信息。
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHander(HttpServletRequest request,Exception e) throws Exception {
        //控制台会输出url和异常
        logger.error("Requst URL : {}, Exception : {}",request.getRequestURI(),e);

        //判断是否存在带状态码的异常，这些异常就由springboot自己处理
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }

        //创建一个ModelAndView对象
        ModelAndView mv=new ModelAndView();
        //添加url，和异常信息
        mv.addObject("url",request.getRequestURI());
        mv.addObject("excption",e);
        //选择到那个页面展示
        mv.setViewName("error/error");

        return mv;
    }
}
