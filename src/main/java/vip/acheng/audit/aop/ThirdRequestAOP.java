package vip.acheng.audit.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import vip.acheng.audit.core.annotation.ThirdURL;
import vip.acheng.audit.util.StringUtil;

import javax.xml.crypto.dsig.SignatureMethod;
import java.lang.reflect.Method;

/**
 * @author 清风徐来
 * @since 2022/8/30 10:17
 */
@Component
@Aspect
@Slf4j
public class ThirdRequestAOP {

    @Autowired
    private RestTemplate restTemplate;

    @Pointcut("@within(vip.acheng.audit.core.annotation.ThirdURL)")
    public void pointcut() {}

    /**
     * TODO 目前只支持Post请求
     * @param joinPoint
     * @return
     * @throws NoSuchMethodException
     */
    @Around("pointcut()")
    public Object handlerThirdRequest(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
        // 获取方法
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = joinPoint.getClass().getDeclaredMethod(methodSignature.getName(), methodSignature.getMethod().getParameterTypes());
        String url = method.getAnnotation(ThirdURL.class).value();
        HttpMethod httpMethod = method.getAnnotation(ThirdURL.class).METHOD();
        // 获取参数
        Object[] args = joinPoint.getArgs();

        // 发送请求
        if (!StringUtil.isBlank(url)) {
            long startTime = System.currentTimeMillis();
            ResponseEntity<?> entity = restTemplate.exchange(url, httpMethod, new HttpEntity<>(args[0]), method.getReturnType());
            log.info("url request cast time: " + (System.currentTimeMillis() - startTime));
            return entity.getBody();
        } else {
            throw new RuntimeException("url cant not blank");
        }
    }
}
