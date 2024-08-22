package com.usehashmap.feign.curl.logger.config;

import com.usehashmap.feign.curl.logger.Slf4jCurlLogger;
import feign.Logger;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.*;

/**
 * Configuration class to create "logger" bean when it's not already present
 */
@AutoConfiguration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "com.usehashmap")
public class CurlLoggerAutoConfiguration {

    @Bean
    @Primary
    @ConditionalOnMissingBean(name = "logger")
    public Logger logger() {
        return new Slf4jCurlLogger();
    }


}