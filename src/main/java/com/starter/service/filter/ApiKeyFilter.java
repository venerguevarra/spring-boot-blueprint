package com.starter.service.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.starter.service.ApplicationConfig;

import lombok.Setter;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@Setter
public class ApiKeyFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(ApiKeyFilter.class);

    private final static String API_KEY_HEADER = "x-api-key";

    @Autowired
    private ApplicationConfig config;

    private List<String> apiKeys;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        apiKeys = config.getApiKeys();
        logger.info("action=init, message=api_key_filter_initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;

        final String uri = httpServletRequest.getRequestURI();
        final String apiKey = getApiKey(httpServletRequest);
        if (shouldNeedApiKey(uri)) {
            logger.info("action=doFilter, message=validate_x_api_key, uri={}", uri);
            if(apiKeys.contains(apiKey)) {
                chain.doFilter(request, response);
            } else {
                ((HttpServletResponse)response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } else {
            logger.info("action=doFilter, message=pass_through_endpoint, uri={}", uri);
            chain.doFilter(request, response);
        }
    }

    private boolean shouldNeedApiKey(final String uri) {
        // Don't authenticate using API key Spring Boot's actuator endpoints and RAML web resources
        return !uri.endsWith("__gtg") && !uri.endsWith("__api")
               && !uri.endsWith("api.raml")
               && !uri.startsWith("/actuator")
               && !uri.startsWith("/web");
    }

    private String getApiKey(HttpServletRequest request) {
        return request.getHeader(API_KEY_HEADER);
    }

    @Override
    public void destroy() {
        logger.info("api_key_filter_destroyed");
    }
}
