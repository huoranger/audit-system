package vip.acheng.audit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import vip.acheng.audit.core.cache.ConfigCache;
import vip.acheng.audit.mapper.ConfigMapper;
import vip.acheng.audit.model.ConfigDO;
import vip.acheng.audit.scheduled.TimedAutoAuditTask;
import vip.acheng.audit.service.ConfigService;

import javax.annotation.PostConstruct;
import java.util.concurrent.ScheduledFuture;

/**
 * @author 清风徐来
 * @since 2022/9/1 11:26
 */
@Service
@Slf4j
@DependsOn("ThreadPoolTaskScheduler")
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, ConfigDO> implements ConfigService {
    @Autowired
    private ConfigMapper configMapper;

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @Autowired
    private ConfigCache configCache;

    @Autowired
    private TimedAutoAuditTask timedAutoAuditTask;

    private ScheduledFuture<?> taskScheduledFuture;

    @PostConstruct
    private void init() {
        configCache.setConfig(list(null).get(0));
        if (configCache.getConfig().isAutomaticAudit()) {
            // 初始化自动审核定时任务
            taskScheduledFuture = threadPoolTaskScheduler.schedule(timedAutoAuditTask, new CronTrigger(TimedAutoAuditTask.TASK_TIME));
        }
    }

    @Override
    public boolean updateAutoAudit() {
        // 打开自动审核添加定时任务，关闭销毁定时任务
        ConfigDO config = configCache.getConfig();
        if (config.isAutomaticAudit()) {
            taskScheduledFuture.cancel(true);
            return configMapper.updateById(config.setAutomaticAudit(false)) == 1;
        }
        taskScheduledFuture = threadPoolTaskScheduler.schedule(timedAutoAuditTask, new CronTrigger(TimedAutoAuditTask.TASK_TIME));
        return configMapper.updateById(configCache.getConfig().setAutomaticAudit(true)) == 1;
    }
}
