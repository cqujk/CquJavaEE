package org.exp4auth.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // 允许cookies跨域
        config.addAllowedOrigin("http://localhost"); // 允许向该服务器的源
       // config.addAllowedOrigin("http://localhost:3000"); // 允许向该服务器的源
        config.addAllowedHeader("*"); // 允许所有的头
        config.addAllowedMethod("*"); // 允许所有的方法
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}

