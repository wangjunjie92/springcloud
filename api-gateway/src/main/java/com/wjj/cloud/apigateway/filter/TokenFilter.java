package com.wjj.cloud.apigateway.filter;

import com.wjj.cloud.apigateway.util.GsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


/**
 * @Author: wangjunjie 2019/3/27 10:55
 * @Description:
 * @Version: 1.0.0
 * @Modified By: xxx 2019/3/27 10:55
 */

@Slf4j
@Component
public class TokenFilter implements GlobalFilter,Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String token = request.getHeaders().getFirst("token");
        if (StringUtils.isNotBlank(token) && "token".equals(token)) {
            return chain.filter(exchange);
        }
        ServerHttpResponse response = exchange.getResponse();
        Map<String, Object> data = new HashMap<>(2);
        data.put("code","401");
        data.put("message","非法请求");
        byte[] datas = GsonUtil.GsonString(data).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(datas);
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
