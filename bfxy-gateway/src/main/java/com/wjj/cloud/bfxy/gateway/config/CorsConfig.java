package com.wjj.cloud.bfxy.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

/**
 * 跨域配置
 * Created by 廖师兄
 * 2018-03-11 23:04
 * C - Cross  O - Origin  R - Resource  S - Sharing
 */
@Configuration
public class CorsConfig {

	@Bean
	public CorsFilter corsFilter() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		final CorsConfiguration config = new CorsConfiguration();

		config.setAllowCredentials(true);
		config.setAllowedOrigins(Arrays.asList("*"));
		config.setAllowedHeaders(Arrays.asList("*"));
		config.setAllowedMethods(Arrays.asList("*"));
		config.setMaxAge(3000L);

		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}
}
