package com.example.zad2.filters;


import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class ValidateUserFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if(request.getSession().getAttribute("user") == null
                && !request.getServletPath().equals("/login")
                && !(request.getServletPath().equals("/mainMenu") && request.getMethod().equals("POST"))
                && !(request.getServletPath().equals("/ussStream") && request.getMethod().equals("POST"))
                && !(request.getServletPath().equals("/servoStream") && request.getMethod().equals("POST"))
                && !(request.getServletPath().equals("/infiltration") && request.getMethod().equals("POST")))
            response.sendRedirect("/login");
        else
            filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}