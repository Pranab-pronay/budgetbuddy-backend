package com.budgetbuddy.config.filter;

import com.budgetbuddy.shared.utils.Utils;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MdcFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        MDC.put("ReqIP", Utils.getClientIp(request));
        MDC.put("ReqURI", request.getRequestURI());
        MDC.put("ReqType", request.getMethod());

        filterChain.doFilter(request, response);

        MDC.remove("ReqIP");
        MDC.remove("ReqURI");
        MDC.remove("ReqType");
    }

}

