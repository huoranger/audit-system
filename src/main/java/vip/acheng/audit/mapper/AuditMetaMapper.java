package vip.acheng.audit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import vip.acheng.audit.model.AuditMetaDO;

import java.util.List;

/**
 * @author 清风徐来
 * @since 2022/8/30 15:51
 */
public interface AuditMetaMapper extends BaseMapper<AuditMetaDO> {

    List<AuditMetaDO> listAuditMetas(@Param("search") AuditMetaDO metaDO,@Param("page") Integer page,@Param("offset") Integer offset);

    List<AuditMetaDO> listAuditedMetas(@Param("search") AuditMetaDO metaDO,@Param("page") Integer page,@Param("offset") Integer offset);

}
