# feign-curl-logger
 Spring boot Starter to log Curl command for Feign clients.

This lib is supposed to work with little to no config, to be able to log your feign client http requests as Curl commands.

### This is a version compatible with Spring boot 2.3.x or before, to use it with Spring 3.x see [release/3.0.0](https://github.com/SimoMarrouss/feign-curl-logger/tree/release/3.0.0), or with 2.7.x see [release/2.7.0](https://github.com/SimoMarrouss/feign-curl-logger/tree/release/2.7.0)
# Install library with:

### Install with 
```xml
<dependency>
    <groupId>com.usehashmap</groupId>
    <artifactId>feign-curl-logger-starter</artifactId>
    <version>2.3.0</version>
</dependency>
```
### Install with Gradle
```groovy
testImplementation 'com.usehashmap:feign-curl-logger-starter:2.0.0'
```

### Logging config
Enable debug logging for the package com.usehashmap:
```properties
logging.level.com.usehashmap=DEBUG
```