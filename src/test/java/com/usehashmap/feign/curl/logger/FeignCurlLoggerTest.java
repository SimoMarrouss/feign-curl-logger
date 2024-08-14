package com.usehashmap.feign.curl.logger;

import feign.Request;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FeignCurlLoggerTest {

    @Test
    void should_return_curl_command_with_headers_and_body_when_request_contains_them() {
        // Given
        Request request = mock(Request.class);
        String url = "http://example.com";
        String method = "POST";
        Map<String, Collection<String>> headers = new LinkedHashMap<>();
        headers.put("Content-Type", List.of("application/json"));
        headers.put("Authorization", List.of("Bearer token"));
        byte[] body = "{\"key\":\"value\"}".getBytes(StandardCharsets.UTF_8);

        when(request.httpMethod()).thenReturn(Request.HttpMethod.valueOf(method));
        when(request.url()).thenReturn(url);
        when(request.headers()).thenReturn(headers);
        when(request.body()).thenReturn(body);

        FeignCurlLogger feignCurlLogger = FeignCurlLogger.from(request);

        // When
        String curlCommand = feignCurlLogger.get();

        // Then
        assertThat(curlCommand).isEqualTo("curl --request POST --location 'http://example.com' " +
                "--header 'Content-Type: application/json' " +
                "--header 'Authorization: Bearer token' " +
                "--data '{\"key\":\"value\"}' ");
    }

    @Test
    void should_return_curl_command_without_body_when_request_has_no_body() {
        // Given
        Request request = mock(Request.class);
        String url = "http://example.com";
        String method = "GET";
        Map<String, Collection<String>> headers = Map.of(
                "Accept", List.of("application/json"));

        when(request.httpMethod()).thenReturn(Request.HttpMethod.valueOf(method));
        when(request.url()).thenReturn(url);
        when(request.headers()).thenReturn(headers);
        when(request.body()).thenReturn(null);

        FeignCurlLogger feignCurlLogger = FeignCurlLogger.from(request);

        // When
        String curlCommand = feignCurlLogger.get();

        // Then
        assertThat(curlCommand).isEqualTo("curl --request GET --location 'http://example.com' " +
                "--header 'Accept: application/json' ");
    }

    @Test
    void should_return_curl_command_without_headers_and_body_when_request_has_none() {
        // Given
        Request request = mock(Request.class);
        String url = "http://example.com";
        String method = "DELETE";

        when(request.httpMethod()).thenReturn(Request.HttpMethod.valueOf(method));
        when(request.url()).thenReturn(url);
        when(request.headers()).thenReturn(Map.of());
        when(request.body()).thenReturn(null);

        FeignCurlLogger feignCurlLogger = FeignCurlLogger.from(request);

        // When
        String curlCommand = feignCurlLogger.get();

        // Then
        assertThat(curlCommand).isEqualTo("curl --request DELETE --location 'http://example.com' ");
    }
}
