package vip.acheng.audit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import vip.acheng.audit.model.AuditMetaDO;
import vip.acheng.audit.model.MovieDO;
import vip.acheng.audit.request.AuditMetaRequest;
import vip.acheng.audit.vo.Paging;

import java.util.List;

/**
 * @author 清风徐来
 * @since 2022/8/30 16:04
 */
public interface AuditMetaService extends IService<AuditMetaDO> {

    Paging<AuditMetaDO> listAuditMetas(Integer page, Integer offset, AuditMetaDO auditMetaDO);

    boolean updateStatus(List<String> ids, boolean isPass, String suggest);

    boolean save(AuditMetaRequest auditMetaRequest);
}
