package com.starter.service;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.starter.service.filter.ApiKeyFilter;

public class ApiKeyFilterTest {

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private HttpServletResponse httpServletResponse;

    @Mock
    private FilterChain filterChain;
    
    @Mock
    private FilterConfig filterConfig;

    @Mock
    private ApplicationConfig config;

    private ApiKeyFilter apiKeyFilter = new ApiKeyFilter();
    
    private List<String> apiKeys;

    @Before
    public void setup() throws ServletException {
        httpServletRequest = mock(HttpServletRequest.class);
        httpServletResponse = mock(HttpServletResponse.class);
        filterChain = mock(FilterChain.class);
        filterConfig = mock(FilterConfig.class);
        config = mock(ApplicationConfig.class);

        apiKeys = new ArrayList<>();
        apiKeys.add("api-key-test-123");

        apiKeyFilter = new ApiKeyFilter();
        apiKeyFilter.setConfig(config);
        apiKeyFilter.init(filterConfig);
    }

    @Test
    public void should_chain_request_to_target_resource() throws IOException, ServletException {
        when(httpServletRequest.getRequestURI()).thenReturn("/actuator/health");
        when(config.getApiKeys()).thenReturn(apiKeys);

        apiKeyFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);
    }

    @Test
    public void should_return_unauthorized_response() throws IOException, ServletException {
        when(httpServletRequest.getRequestURI()).thenReturn("/resources");

        apiKeyFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);

        verify(httpServletResponse, atLeast(1)).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    @Test
    public void should_return_authorized_request() throws IOException, ServletException {
        when(httpServletRequest.getHeader("x-api-key")).thenReturn("api-key-test-123");
        when(httpServletRequest.getRequestURI()).thenReturn("/resources");

        apiKeyFilter.doFilter(httpServletRequest, httpServletResponse, filterChain);
        
        verify(httpServletResponse, atLeast(0)).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

}
