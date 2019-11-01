package com.adeliosys.b3issue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;

/**
 * Log the B3 headers.
 * <p/>
 * This filter should use higher precedence than Spring Cloud Sleuth,
 * see {@link org.springframework.cloud.sleuth.instrument.web.TraceWebServletAutoConfiguration}
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 3)
public class RequestLogFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestLogFilter.class);

    private static final String[] HEADER_NAMES = {"X-B3-TraceId", "X-B3-SpanId", "X-B3-ParentSpanId", "B3"};

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        try {
            LOGGER.info("B3 headers (before delegation): {}", getB3HeaderInfo(req, res));
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            LOGGER.info("B3 headers (after delegation): {}", getB3HeaderInfo(req, res));
        }
    }

    private String getB3HeaderInfo(HttpServletRequest req, HttpServletResponse res) {
        StringBuilder builder = new StringBuilder(256);

        Arrays.stream(HEADER_NAMES).forEach(name -> {
            Enumeration<String> values = req.getHeaders(name);
            while (values.hasMoreElements()) {
                builder.append(name);
                builder.append('=');
                builder.append(values.nextElement());
                builder.append(' ');
            }
        });

        return builder.toString();
    }
}
