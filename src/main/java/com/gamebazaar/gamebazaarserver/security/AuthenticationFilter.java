package com.gamebazaar.gamebazaarserver.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AuthenticationFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        String requestURI = ((HttpServletRequest) request).getRequestURI();

        List<String> allowed = new ArrayList<>();
        allowed.add("/register");
        allowed.add("/login");
        allowed.add("/search");

        if (allowed.contains(requestURI)) {
            filterChain.doFilter(request, response); // Allow the request to continue
            return;
        }

        try {
            Authentication authentication = AuthenticationController.getAuthentication((HttpServletRequest) request);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception exp) {
            /*
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            PrintWriter writer = httpResponse.getWriter();
            writer.print(exp.getMessage());
            writer.flush();
            writer.close();
             */
            //System.out.println("Unauthorized request from '"+request.getRemoteAddr()+"' has been declined at "+new Date());
        }

        filterChain.doFilter(request, response);
    }
}