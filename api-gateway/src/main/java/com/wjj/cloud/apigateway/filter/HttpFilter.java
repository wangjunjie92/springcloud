package com.wjj.cloud.apigateway.filter;

import com.wjj.cloud.apigateway.util.GsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: wangjunjie 2019/3/27 11:31
 * @Description:
 * @Version: 1.0.0
 * @Modified By: xxx 2019/3/27 11:31
 */

@Slf4j
@Component
public class HttpFilter implements GlobalFilter,Ordered {

    private static final String START_TIME = "startTime";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        URI uri = exchange.getRequest().getURI();
        //只记录http的请求
        String scheme = uri.getScheme();
        if ((!"http".equals(scheme) && !"https".equals(scheme))) {
            return chain.filter(exchange);
        }

        String methodName = exchange.getRequest().getMethod().name();
        String host = exchange.getRequest().getURI().getHost();
        String path = exchange.getRequest().getURI().getPath();
        String param = GsonUtil.GsonString(exchange.getRequest().getQueryParams());
        exchange.getAttributes().put(START_TIME,System.currentTimeMillis());
        return chain.filter(exchange).then(Mono.fromRunnable(()-> {
            Long startTime = exchange.getAttribute(START_TIME);
            if (startTime!=null) {
                Long executeTime = (System.currentTimeMillis() -startTime);
                log.info("\n\t请求路径:{}\n\t请求参数:{}\n\t请求类型:{}\n\t请求ip:{}\n\t请求时间:{}",
                        path,param,methodName,host,executeTime);
            }
        }));
    }


    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
