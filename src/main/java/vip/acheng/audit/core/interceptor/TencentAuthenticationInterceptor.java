package vip.acheng.audit.core.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.experimental.Accessors;
import org.json.JSONObject;
import org.springframework.web.servlet.HandlerInterceptor;
import vip.acheng.audit.core.cache.ConfigCache;
import vip.acheng.audit.core.filter.AuditMetaRequestWrapper;
import vip.acheng.audit.util.EncryptUtil;
import vip.acheng.audit.util.JsonUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author 清风徐来
 * @since 2022/9/1 10:39
 */
@Data
@Accessors(chain = true)
public class TencentAuthenticationInterceptor implements HandlerInterceptor {
    private ConfigCache configCache;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // TODO 方便测试，记得删除
        if (true) {
            return true;
        }
        // 加密信息
        AuditMetaRequestWrapper auditMetaRequestWrapper = (AuditMetaRequestWrapper) request;
        String sign = JsonUtil.string2Map(auditMetaRequestWrapper.getBody()).get("sign").toString();
        String auditId = JsonUtil.string2Map(auditMetaRequestWrapper.getBody()).get("audit_id").toString();
        // 校验是否放行
        return EncryptUtil.SHA256HMAC(auditId, configCache.getAuditSecret()).equals(sign);
    }
}
