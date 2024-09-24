package com.usehashmap.feign.curl.logger;

import feign.Request;

import java.util.Collection;
import java.util.Map;

/**
 * This helper class takes a request and return the corresponding Curl command as String
 */
public class FeignCurlLogger {

    private final Request request;

    /**
     * private constructor, use static factory method
     *
     * @param request Feign request
     */
    private FeignCurlLogger(Request request) {
        this.request = request;
    }

    /**
     * Static factory method create an instance of {@link FeignCurlLogger}
     *
     * @param request Feign request to handle
     * @return an instance of this {@link FeignCurlLogger}
     */
    public static FeignCurlLogger from(Request request) {
        return new FeignCurlLogger(request);
    }


    /**
     * Extract the curl command from the feign request
     *
     * @return the Curl command
     */
    public String get() {
        StringBuilder curlLog = new StringBuilder();
        curlLog.append("curl ")
                .append("--request ").append(request.httpMethod().name()).append(" ")
                .append("--location '").append(request.url()).append("' ");

        for (Map.Entry<String, Collection<String>> header : request.headers().entrySet()) {
            curlLog.append("--header '").append(header.getKey()).append(": ").append(header.getValue().iterator().next()).append("' ");
        }

        if (request.body() != null) {
            curlLog.append("--data '").append(new String(request.body())).append("' ");
        }

        return curlLog.toString();
    }
}