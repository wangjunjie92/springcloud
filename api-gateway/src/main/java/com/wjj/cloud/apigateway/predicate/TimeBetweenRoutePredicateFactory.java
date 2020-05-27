package com.wjj.cloud.apigateway.predicate;


import lombok.extern.slf4j.Slf4j;

import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;


import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * @Author: wangjunjie 2019/8/15 10:38
 * @Description:
 * @Version: 1.0.0
 * @Modified By: xxx 2019/8/15 10:38
 */

@Component
@Slf4j
public class TimeBetweenRoutePredicateFactory extends AbstractRoutePredicateFactory<TimeBeweenConfig> {

    public TimeBetweenRoutePredicateFactory() {
        super(TimeBeweenConfig.class);
    }

    @Override
    public Predicate<ServerWebExchange> apply(TimeBeweenConfig config) {
        LocalTime start = config.getStart();
        LocalTime end = config.getEnd();
        return exchange -> {
            log.info("aaaaaaaaaaaaaaaaaaa");
            LocalTime now = LocalTime.now();
            return now.isAfter(start) && now.isBefore(end);
        };
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("start","end");
    }

    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
        System.out.println(formatter.format(LocalTime.now()));
    }
}
