package vip.acheng.audit.core.cache;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import vip.acheng.audit.model.ConfigDO;

/**
 * @author 清风徐来
 * @since 2022/9/1 13:57
 */
@Component
@Data
public class ConfigCache {

    @Value("${tencent.auditSecret}")
    private String auditSecret;

    private ConfigDO config;

}
