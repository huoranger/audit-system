package vip.acheng.audit.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.acheng.audit.vo.Result;

/**
 * @author 清风徐来
 * @since 2022/8/30 9:39
 */
@RestController
public class PingController {

    @GetMapping("ping")
    public Result ping() {
        return new Result(20000, "成功", "ping");
    }
}
