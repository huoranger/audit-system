package vip.acheng.audit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vip.acheng.audit.core.annotation.ThirdAPI;
import vip.acheng.audit.model.AuditMetaDO;
import vip.acheng.audit.request.AuditMetaPageRequest;
import vip.acheng.audit.request.AuditMetaRequest;
import vip.acheng.audit.request.ProcessAuditRequest;
import vip.acheng.audit.service.AuditMetaService;
import vip.acheng.audit.vo.Paging;
import vip.acheng.audit.vo.Result;
import vip.acheng.audit.vo.TencentResultVO;


/**
 * @author 清风徐来
 * @since 2022/8/30 15:54
 */
@RestController
@RequestMapping("audit")
public class AuditMetaController {

    @Autowired
    private AuditMetaService auditMetaService;

    @PostMapping("list")
    public Result list(@RequestBody AuditMetaPageRequest pageRequest) {
        Paging<AuditMetaDO> paging = auditMetaService.listAuditMetas(pageRequest.getPage(), pageRequest.getOffset(), pageRequest.getAuditMetaDO());
        return new Result(20000, "成功", paging);
    }

    @ThirdAPI
    @PostMapping("send")
    public TencentResultVO send(@RequestBody AuditMetaRequest auditMetaRequest) {
        boolean flag = auditMetaService.save(auditMetaRequest);
        return new TencentResultVO(flag? 0 : -1, flag ? "ok": "fail");
    }

    @PostMapping("process_audit")
    public Result updateStatus(@RequestBody ProcessAuditRequest processAuditRequest) {
        boolean flag = auditMetaService.updateStatus(processAuditRequest.getIds(), processAuditRequest.isPass(), processAuditRequest.getSuggest());
        return new Result(flag? 20000: 50001, flag? "成功": "失败", null);
    }
}
