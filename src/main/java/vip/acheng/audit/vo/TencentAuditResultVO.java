package vip.acheng.audit.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author 清风徐来
 * @since 2022/9/1 16:33
 */
@Data
@Accessors(chain = true)
public class TencentAuditResultVO {
    @JsonProperty("audit_id")
    private String auditId;
    private String msg;
    private int classification;
    private String sign;
}
