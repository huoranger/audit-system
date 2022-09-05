package vip.acheng.audit.scheduled;

import com.mysql.cj.log.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import vip.acheng.audit.core.cache.ConfigCache;
import vip.acheng.audit.model.AuditMetaDO;
import vip.acheng.audit.service.AuditMetaService;
import vip.acheng.audit.util.EncryptUtil;
import vip.acheng.audit.vo.TencentAuditResultVO;

import java.util.List;

/**
 * @author 清风徐来
 * @since 2022/9/1 11:17
 */
@Slf4j
@Component
public class TimedAutoAuditTask implements Runnable{
    public static final String TASK_TIME = "*/60 * * * * *";

    @Autowired
    private AuditMetaService auditMetaService;

    @Autowired
    private ConfigCache configCache;

    @Override
    public void run() {
        // 获取为审核的所有数据
        List<AuditMetaDO> auditMetas = auditMetaService.listAuditMetas(null, null, new AuditMetaDO().setAuditStatus(0)).getData();

        auditMetas.forEach(auditMeta -> {
            long timeInterval = System.currentTimeMillis() - auditMeta.getCreateTime().getTime();
            // 判断是否经过一次智能审核
            if (!auditMeta.isPassAutoAudit()) {
                // 判断是否达到一次人工审核时间
                if (timeInterval > configCache.getConfig().getAuditTime() * 60 * 1000) {
                    // 判断是否有敏感词
                    if (!auditMeta.isSensitiveWord()) {
                        // 判断是否有黑名单
                        if (!auditMeta.isBackList()) {
                            // 更新审核阶段为初审
                            auditMetaService.updateById(new AuditMetaDO().setAuditId(auditMeta.getAuditId()).setStatus(1).setAuditStatus(1).setAuditor("zhiban"));
                            log.info("数据{}通过智能审核",auditMeta);
                            return;
                        }
                    }
                    // 标记为已经经过一次智能审核
                    auditMetaService.updateById(new AuditMetaDO().setPassAutoAudit(true));
                    // 创建返回给腾讯的信息对象
                    TencentAuditResultVO tencentAuditResultVO = new TencentAuditResultVO().setAuditId(auditMeta.getAuditId()).setMsg("通过").setClassification(1)
                            .setSign(EncryptUtil.SHA256HMAC(auditMeta.getAuditId(), configCache.getAuditSecret()));
                    // TODO 给腾讯返回信息 调用接口
                }
            }
        });
    }
}
