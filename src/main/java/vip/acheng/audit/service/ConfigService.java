package vip.acheng.audit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import vip.acheng.audit.model.ConfigDO;

import java.util.concurrent.ScheduledFuture;

/**
 * @author 清风徐来
 * @since 2022/9/1 11:25
 */
public interface ConfigService extends IService<ConfigDO> {

    boolean updateAutoAudit();
}
