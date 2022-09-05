package vip.acheng.audit.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import vip.acheng.audit.model.AuditMetaDO;
import vip.acheng.audit.model.MovieDO;

/**
 * @author 清风徐来
 * @since 2022/8/30 16:11
 * 管理系统的请求VO
 */
@Data
public class AuditMetaPageRequest {
    private int page;
    private int offset;
    @JsonProperty("audit_meta")
    private AuditMetaDO auditMetaDO;
}
