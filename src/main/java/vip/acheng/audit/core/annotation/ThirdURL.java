package vip.acheng.audit.core.annotation;

import org.springframework.http.HttpMethod;

import java.lang.annotation.*;

/**
 * @author 清风徐来
 * @since 2022/8/30 10:06
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ThirdURL {
    String value();

    HttpMethod METHOD();

}
