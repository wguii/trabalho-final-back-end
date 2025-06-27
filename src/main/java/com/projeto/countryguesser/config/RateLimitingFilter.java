package com.projeto.countryguesser.config;

import com.projeto.countryguesser.service.RateLimiterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RateLimitingFilter implements Filter {

    @Autowired
    private RateLimiterService rateLimiterService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String clientIp = req.getRemoteAddr(); // ou userId se estiver autenticado

        if (!rateLimiterService.isRequestAllowed(clientIp)) {
            res.setStatus(429); // Too Many Requests
            res.getWriter().write("Limite de requisições atingido. Tente novamente mais tarde.");
            return;
        }

        chain.doFilter(request, response);
    }
}
