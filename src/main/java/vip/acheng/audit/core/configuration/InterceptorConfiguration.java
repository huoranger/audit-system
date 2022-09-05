package vip.acheng.audit.core.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import vip.acheng.audit.core.cache.ConfigCache;
import vip.acheng.audit.core.interceptor.AuthenticationInterceptor;
import vip.acheng.audit.core.interceptor.TencentAuthenticationInterceptor;

/**
 * @author 清风徐来
 * @since 2022/9/1 16:53
 */
@Configuration
public class InterceptorConfiguration extends WebMvcConfigurationSupport {

    @Autowired
    private ConfigCache configCache;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TencentAuthenticationInterceptor().setConfigCache(configCache))
                .addPathPatterns("/audit/send");
        registry.addInterceptor(new AuthenticationInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/user/auth");
    }
}
