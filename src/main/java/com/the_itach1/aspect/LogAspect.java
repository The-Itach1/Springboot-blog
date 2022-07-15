package com.the_itach1.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

//切面
@Aspect
@Component
public class LogAspect {

    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    //切点，拦截那些类,拦截com.the_itach1.web软件包中的所有类和方法。
    @Pointcut("execution(* com.the_itach1.web.*.*(..))")
    public void log(){
    }

    //在所有请求开始前执行doBerfore
    @Before("log()")
    public void doBefore(JoinPoint joinPoint)
    {
        ServletRequestAttributes attributes= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request=attributes.getRequest();

        //获取url
        String url=request.getRequestURI();
        //获取访问的ip地址
        String ip=request.getRemoteAddr();
        //获取方法名称
        String classMethod= joinPoint.getSignature().getDeclaringTypeName()+ "."+ joinPoint.getSignature().getName();
        //获取参数
        Object[] args=joinPoint.getArgs();
        //创建我们的Requestlog类，返回字符串
        Requestlog requestlog=new Requestlog(url,ip,classMethod,args);

        //在日志中打印出来
        logger.info("Request : {}",requestlog);
        //logger.info("-----------doBefore------------");
    }

    //请求执行后
    @After("log()")
    public void doAfter()
    {
        //logger.info("-----------doAfter-------------");
    }

    //执行后的返回值
    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterReturn(Object result)
    {
        logger.info("Result : {}" , result);
    }

    private class Requestlog {
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public Requestlog(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "Requestlog{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }


}