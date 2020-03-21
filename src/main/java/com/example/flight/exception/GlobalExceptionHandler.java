package com.example.flight.exception;

import com.example.flight.model.error.ErrorInfo;;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = MyException.class)
    @ResponseBody
    public ErrorInfo jsonErrorHandler(HttpServletRequest req, MyException e) throws Exception {
        log.info("执行jsonErrorHandler");
        ErrorInfo r = new ErrorInfo();
        r.setMessage(e.getMessage());
        r.setCode(ErrorInfo.ERROR);
        r.setData("自定义MyException异常");
        r.setUrl(req.getRequestURL().toString());

        log.error(e.getMessage());
        return r;
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ErrorInfo exceptionHandler(HttpServletRequest req, Exception e){
        log.info("执行exceptionHandler");
        ErrorInfo r = new ErrorInfo();
        r.setMessage(e.getMessage());
        r.setCode(ErrorInfo.ERROR);
        r.setData("Exception异常");
        r.setUrl(req.getRequestURL().toString());

        e.printStackTrace();
        log.error(e.getMessage());
        return r;
    }

}
