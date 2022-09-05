package vip.acheng.audit.core.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author 清风徐来
 * @since 2022/9/2 10:47
 */
@Configuration
public class CrossOriginConfiguration {

    private CorsConfiguration corsConfig(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedHeader("*"); //允许所有域名访问
        corsConfiguration.addAllowedMethod("*"); //允许所有请求头
        corsConfiguration.addAllowedOrigin("*"); //允许所有的请求类型
        corsConfiguration.setMaxAge(3600L);
        return corsConfiguration;
    }
    @Bean
    public CorsFilter corsFilter(){
        //存储request与跨域配置信息的容器，基于url的映射
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",corsConfig());
        return new CorsFilter(source);
    }

}
