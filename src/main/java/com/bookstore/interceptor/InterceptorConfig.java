package com.bookstore.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    public InterceptorConfig() {
    }

    @Bean
    public SessionValidateInterceptor sessionValidateInterceptor() {
        return new SessionValidateInterceptor();
    }

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.sessionValidateInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login")
                .excludePathPatterns("/register")
                .excludePathPatterns("/home")
                .excludePathPatterns("/getBooks")
                .excludePathPatterns("/getBookById")
                .excludePathPatterns("/getBooksByPage")
                .excludePathPatterns("/getHomeContent")
                .excludePathPatterns("/register")
                .excludePathPatterns("/checkSession")
                .excludePathPatterns("/registerCheck")
                .excludePathPatterns("/getPageView");
    }


    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");  /*允许访问的方法名,GET POST等*/
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true); /*是否允许请求带有验证信息*/
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", this.buildConfig());
        return new CorsFilter(source);
    }
}
