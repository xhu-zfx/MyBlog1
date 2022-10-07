package com.panghu.blog.aspect;

import cn.hutool.json.JSONUtil;
import com.panghu.blog.annotation.SystemLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xhu-zfx
 * @email <756867768@qq.com>
 * @date 2022/9/21 11:50
 * @description  日志打印切面类
 */
//@Component
//@Aspect
@Slf4j
public class logAspect {

    @Pointcut("@annotation(com.panghu.blog.annotation.SystemLog)")
    public void pointcut(){

    }

    @Around("pointcut()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object proceed ;
        try {
            handleBefore(joinPoint);
            proceed= joinPoint.proceed();
            handleAfter(proceed);
        }finally {
            // 结束后换行
            log.info("=======APO LOG END=======" + System.lineSeparator());
        }
        return proceed;
    }

    private void handleAfter(Object proceed) {
        // 打印出参
        log.info("Response       : {}", JSONUtil.toJsonStr(proceed));
    }

    private void handleBefore(ProceedingJoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        SystemLog systemLog= getSystemLog(joinPoint);
        log.info("=======APO LOG START=======");
        // 打印请求 URL
        log.info("URL            : {}",request.getRequestURL());
        // 打印描述信息
        log.info("BusinessName   : {}",systemLog.businessName());
        // 打印 Http method
        log.info("HTTP Method    : {}",request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        log.info("Class Method   : {}.{}",joinPoint.getSignature().getDeclaringType(),((MethodSignature)joinPoint.getSignature()).getName() );
        // 打印请求的 IP
        log.info("IP             : {}",request.getRemoteHost());
        // 打印请求入参
        log.info("Request Args   : {}", JSONUtil.toJsonStr(joinPoint.getArgs()));
    }

    private SystemLog getSystemLog(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        return signature.getMethod().getAnnotation(SystemLog.class);
    }
}
