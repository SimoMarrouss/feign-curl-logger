package com.usehashmap.feign.curl.logger.aspect;

import com.usehashmap.feign.curl.logger.FeignCurlLogger;
import feign.Request;
import feign.Response;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

/**
 * An aspect to go around {@link feign.Logger} to log the curl command
 */
@Aspect
@Component
@ConditionalOnBean(name = "logger")
@Slf4j
public class CurlLoggerAdvice {

    /**
     * This is the pointcut that's applied on the bean "logger" and when calling the method feign.Logger#logRequest
     */
    @Pointcut("bean(logger) && !execution(* feign.Logger.logRequest(..))")
    public void inFeignLogger() {
        // do nothing because its a @Pointcut
    }

    /**
     * This is the around of {@link CurlLoggerAdvice#inFeignLogger()}
     *
     * @param thisJoinPoint instance of ProceedingJoinPoint
     * @return the result of proceeding the execution
     * @throws Throwable
     */
    @Around("inFeignLogger()")
    public Object aroundEveryFeignLogger(ProceedingJoinPoint thisJoinPoint) throws Throwable {

        try {
            Request request = ((Response) thisJoinPoint.getArgs()[2]).request();
            log.info("Curl logging...");
            log.info(FeignCurlLogger.from(request).get());
        } catch (Exception e) {
            // Ignoring the exception and continue the execution
        }

        return thisJoinPoint.proceed();
    }
}
