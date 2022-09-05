package vip.acheng.audit.core.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import vip.acheng.audit.vo.Result;

/**
 * @author 清风徐来
 * @since 2022/9/2 23:57
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        System.out.println(e);
        log.error("全局错误处理:{}", e.getMessage());
        return new Result(50000, "错误的操作或后台程序有bug", null);
    }
}
