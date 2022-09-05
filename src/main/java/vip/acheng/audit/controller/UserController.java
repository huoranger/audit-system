package vip.acheng.audit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.web.bind.annotation.*;
import vip.acheng.audit.request.UserAuthRequest;
import vip.acheng.audit.service.third.UserThirdService;
import vip.acheng.audit.util.AuthUtil;
import vip.acheng.audit.vo.Result;

import javax.servlet.http.HttpServletRequest;
import java.awt.peer.LightweightPeer;
import java.util.HashMap;

/**
 * @author 清风徐来
 * @since 2022/9/2 9:24
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserThirdService userThirdService;

    @PostMapping("auth")
    public Result auth(@RequestBody UserAuthRequest userAuthRequest) {
        // 调用第三方认证接口
        if (userThirdService.login(userAuthRequest.getUsername(), userAuthRequest.getPassword())) {
            return new Result(20000, "成功", AuthUtil.getToken(new HashMap<String, String>(){{put("username", userAuthRequest.getUsername());}}));
        }
        return new Result(20002, "认证失败", null);
    }

    @GetMapping("get_user_info")
    public Result getUserInfo() {
        return new Result(20000, "成功", UserThirdService.USERNAME.get());
    }
}
