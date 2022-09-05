package vip.acheng.audit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vip.acheng.audit.core.cache.ConfigCache;
import vip.acheng.audit.service.ConfigService;
import vip.acheng.audit.vo.Result;

/**
 * @author 清风徐来
 * @since 2022/9/1 13:34
 */
@RestController
@RequestMapping("config")
public class ConfigController {

    @Autowired
    private ConfigService configService;

    @Autowired
    private ConfigCache configCache;

    @PutMapping("on_auto_audit")
    public Result onAutoAudit() {
        boolean flag = configService.updateAutoAudit();
        return new Result(flag ? 20000: 50001, flag? "成功": "失败", null);
    }

    @GetMapping("auto_audit_status")
    public Result getAutoAuditStatus() {
        return new Result(20000, "成功", configCache.getConfig().isAutomaticAudit());
    }

}
