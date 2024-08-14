package com.usehashmap.feign.curl.logger.aspect;

import feign.Request;
import feign.Response;
import nl.altindag.log.LogCaptor;
import nl.altindag.log.model.LogEvent;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurlLoggerAdviceTest {

    @Mock
    private ProceedingJoinPoint proceedingJoinPoint;

    @Mock
    private Response response;

    @Mock
    private Request request;

    @InjectMocks
    private CurlLoggerAdvice curlLoggerAdvice;

    @BeforeEach
    void setUp() {

    }

    @Test
    void should_log_curl_request_and_proceed_when_feign_logger_is_invoked() throws Throwable {
        // Given
        when(response.request()).thenReturn(request);
        when(proceedingJoinPoint.getArgs()).thenReturn(new Object[]{"arg1", "arg2", response});
        when(request.headers()).thenReturn(Map.of("header1", List.of("header1-value")));
        when(request.url()).thenReturn("http://localhost");
        when(request.httpMethod()).thenReturn(Request.HttpMethod.GET);
        String curlCommand = "curl --request GET --location 'http://localhost' --header 'header1: header1-value' ";
        LogCaptor logCaptor = LogCaptor.forClass(CurlLoggerAdvice.class);
        when(proceedingJoinPoint.proceed()).thenReturn("proceed result");

        // When
        curlLoggerAdvice.aroundEveryFeignLogger(proceedingJoinPoint);

        // Then
        List<LogEvent> logEvents = logCaptor.getLogEvents();
        assertThat(logEvents).hasSize(2);
        assertThat(logEvents.get(1).getMessage()).isEqualTo(curlCommand);
        verify(proceedingJoinPoint).proceed();
    }

    @Test
    void should_continue_execution_when_exception_is_thrown_in_curl_logging() throws Throwable {
        // Given
        when(proceedingJoinPoint.getArgs()).thenReturn(null);
        when(proceedingJoinPoint.proceed()).thenReturn("proceed result");
        LogCaptor logCaptor = LogCaptor.forClass(CurlLoggerAdvice.class);

        // When
        curlLoggerAdvice.aroundEveryFeignLogger(proceedingJoinPoint);

        // Then
        List<LogEvent> logEvents = logCaptor.getLogEvents();
        assertThat(logEvents).isEmpty();
        verify(proceedingJoinPoint).proceed();
    }
}
