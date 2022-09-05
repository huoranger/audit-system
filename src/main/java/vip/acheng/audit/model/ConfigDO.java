package vip.acheng.audit.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author 清风徐来
 * @since 2022/8/31 9:39
 */
@TableName("config")
@Getter
@Setter
@Accessors(chain = true)
public class ConfigDO {

    private Integer id;
    private Integer sendWechatTime;
    private Integer auditTime;
    private boolean automaticAudit;
}
