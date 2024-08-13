package com.usehashmap.feign.curl.logger.aspect;

import com.usehashmap.feign.curl.logger.FeignCurlLogger;
import feign.Request;
import feign.Response;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CurlLoggerAdvice {

    @Pointcut("bean(logger) && !execution(* feign.Logger.logRequest(..))")
    public void inFeignLogger() {
        // do nothing because its a @Pointcut
    }

    @Around("inFeignLogger()")
    public Object aroundEveryController(ProceedingJoinPoint thisJoinPoint) throws Throwable {

        Request request = ((Response) thisJoinPoint.getArgs()[2]).request();

        System.out.println("curl logging...");
        System.out.println(FeignCurlLogger.from(request).get());

        return thisJoinPoint.proceed();
    }
}
