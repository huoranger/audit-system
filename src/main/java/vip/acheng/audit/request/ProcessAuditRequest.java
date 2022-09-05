package vip.acheng.audit.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 清风徐来
 * @since 2022/9/1 16:05
 */
@Data
public class ProcessAuditRequest {
    @JsonProperty("audit_ids")
    private List<String> ids;
    @JsonProperty("is_pass")
    private boolean isPass;
    private String suggest;
}
