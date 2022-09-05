package vip.acheng.audit.core.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import vip.acheng.audit.core.annotation.ThirdAPI;
import vip.acheng.audit.service.third.UserThirdService;
import vip.acheng.audit.util.AuthUtil;
import vip.acheng.audit.util.JsonUtil;
import vip.acheng.audit.vo.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 清风徐来
 * @since 2022/9/2 9:57
 */
public class AuthenticationInterceptor implements HandlerInterceptor {

    private static final String AUTH_HEADER = "AUTHORIZE_TOKEN";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 第三方接口放行
        if (((HandlerMethod) handler).getMethodAnnotation(ThirdAPI.class) != null) {
            return true;
        }
        // 获取token，并校验
        String token = request.getHeader(AUTH_HEADER);
        try {
            AuthUtil.verifyToken(token);
            // 保存用户信息
            UserThirdService.USERNAME.set(AuthUtil.getClaimByName(token, "username").asString());
            return true;
        } catch (Exception e) {
            String result = JsonUtil.object2String(new Result(20003, "token失效", null));
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println(result);
            return false;
        }

    }
}
