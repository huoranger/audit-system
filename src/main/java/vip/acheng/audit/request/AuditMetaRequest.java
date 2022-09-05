package vip.acheng.audit.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import vip.acheng.audit.model.MovieDO;

import java.util.List;

/**
 * @author 清风徐来
 * @since 2022/8/31 11:00
 * 提供给腾讯接口的接口的请求VO
 */
@Data
public class AuditMetaRequest {
    @JsonProperty("audit_id")
    private String auditId;
    @JsonProperty("push_man")
    private String pushMan;
    @JsonProperty("audit_type")
    private String auditType;
    @JsonProperty("audit_cons")
    private List<MovieDO> movies;
}
