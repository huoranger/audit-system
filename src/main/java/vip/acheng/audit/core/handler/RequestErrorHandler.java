package vip.acheng.audit.core.handler;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.acheng.audit.vo.Result;

@RestController
public class RequestErrorHandler implements ErrorController {

    @GetMapping("error")
    public Result error() {
        return new Result(50000, "错误的操作", null);
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}