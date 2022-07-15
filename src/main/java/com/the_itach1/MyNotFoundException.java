package com.the_itach1;

//定义一个自己的异常处理类，继承RuntimeException，来捕获异常

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//为了跳到404页面，还需要一个返回的状态吗，也就是HttpStatus.NOT_FOUND，这样就能跳到404.html
@ResponseStatus(HttpStatus.NOT_FOUND)
public class MyNotFoundException extends RuntimeException{
    public MyNotFoundException() {
        super();
    }

    public MyNotFoundException(String message) {
        super(message);
    }

    public MyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
