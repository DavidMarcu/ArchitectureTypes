package com.dmarcu.layered.presentaion.filters;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CORSFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        var httpServletResponse = (HttpServletResponse) servletResponse;
//        httpServletResponse.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "http://localhost:8081");
//        httpServletResponse.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "content-type");
//        httpServletResponse.addHeader(HttpHeaders.VARY, "Origin");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
