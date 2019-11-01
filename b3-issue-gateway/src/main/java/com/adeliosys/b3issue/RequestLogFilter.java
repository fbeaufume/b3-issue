package com.adeliosys.b3issue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Arrays;

/**
 * Log the B3 headers.
 * <p/>
 * This filter should use a high precedence, but lower than Spring Cloud Sleuth,
 * see {@link org.springframework.cloud.sleuth.instrument.web.TraceHttpAutoConfiguration}
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 3)
public class RequestLogFilter implements WebFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestLogFilter.class);

    private static final String[] HEADER_NAMES = {"X-B3-TraceId", "X-B3-SpanId", "X-B3-ParentSpanId", "B3"};

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        LOGGER.info("B3 headers (before delegation): {}", getB3HeaderInfo(exchange));

        return chain.filter(exchange).doOnTerminate(() -> {
            LOGGER.info("B3 headers (after delegation): {}", getB3HeaderInfo(exchange));
        });
    }

    private String getB3HeaderInfo(ServerWebExchange exchange) {
        StringBuilder builder = new StringBuilder(128);

        Arrays.stream(HEADER_NAMES).forEach(name -> {
            exchange.getRequest().getHeaders().getValuesAsList(name).forEach(value -> {
                builder.append(name);
                builder.append('=');
                builder.append(value);
                builder.append(' ');
            });
        });

        return builder.toString();
    }
}