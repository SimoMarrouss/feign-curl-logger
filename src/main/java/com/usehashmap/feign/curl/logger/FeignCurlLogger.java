package com.usehashmap.feign.curl.logger;

import feign.Request;

import java.util.Collection;
import java.util.Map;

public class FeignCurlLogger {

    private final Request request;

    private FeignCurlLogger(Request request) {
        this.request = request;
    }

    public static FeignCurlLogger from(Request request) {
        return new FeignCurlLogger(request);
    }

    public String get() {
        StringBuilder curlLog = new StringBuilder();
        curlLog.append("curl ")
                .append("--request ").append(request.httpMethod().name()).append(" ")
                .append("--location '").append(request.url()).append("' ");

        for (Map.Entry<String, Collection<String>> header : request.headers().entrySet()) {
            curlLog.append("--header '").append(header.getKey()).append(": ").append(header.getValue().iterator().next()).append("' ");
        }

        return curlLog.toString();
    }
}