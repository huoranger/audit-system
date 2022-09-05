package vip.acheng.audit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.acheng.audit.core.cache.ConfigCache;
import vip.acheng.audit.mapper.AuditMetaMapper;
import vip.acheng.audit.model.AuditMetaDO;
import vip.acheng.audit.model.MovieDO;
import vip.acheng.audit.model.PictureDO;
import vip.acheng.audit.request.AuditMetaRequest;
import vip.acheng.audit.service.AuditMetaService;
import vip.acheng.audit.service.MovieService;
import vip.acheng.audit.service.PictureService;
import vip.acheng.audit.util.BeanUtil;
import vip.acheng.audit.util.EncryptUtil;
import vip.acheng.audit.util.StringUtil;
import vip.acheng.audit.vo.Paging;
import vip.acheng.audit.vo.TencentAuditResultVO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 清风徐来
 * @since 2022/8/30 16:05
 */
@Service
@Slf4j
public class AuditMetaServiceImpl extends ServiceImpl<AuditMetaMapper, AuditMetaDO> implements AuditMetaService {

    @Autowired
    private ConfigCache configCache;

    @Autowired
    private AuditMetaMapper auditMetaMapper;

    @Autowired
    private MovieService movieService;

    @Autowired
    private PictureService pictureService;

    @Override
    public Paging<AuditMetaDO> listAuditMetas(Integer page, Integer offset, AuditMetaDO auditMetaDO) {
        List<AuditMetaDO> auditMetas;
        long count;
        if (auditMetaDO.getStatus() == null || auditMetaDO.getStatus() != 2) {
            // 不使用分页插件，这里使用分页插件查询出来的记录不符合要求
            auditMetas = auditMetaMapper.listAuditMetas(auditMetaDO, page, offset);
            count = countTotal(auditMetaDO);
        } else {
            // status为2，查询已审核的数据
            auditMetas = auditMetaMapper.listAuditedMetas(auditMetaDO, page, offset);
            count = countAuditedTotal(auditMetaDO);
        }
        return new Paging<>(page, auditMetas.size(), count, auditMetas);
    }

    // TODO 有时间将SQL提取到SQL文件
    private long countTotal(AuditMetaDO auditMetaDO) {
        LambdaQueryWrapper<AuditMetaDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtil.isNotBlank(auditMetaDO.getAuditId()), AuditMetaDO::getAuditId, auditMetaDO.getAuditId())
                .like(StringUtil.isNotBlank(auditMetaDO.getPushMan()), AuditMetaDO::getPushMan, auditMetaDO.getPushMan())
                .like(StringUtil.isNotBlank(auditMetaDO.getAuditor()), AuditMetaDO::getAuditor, auditMetaDO.getAuditor())
                .eq(auditMetaDO.getStatus() != null, AuditMetaDO::getStatus, auditMetaDO.getStatus())
                .eq(auditMetaDO.getAuditStatus() != null, AuditMetaDO::getAuditStatus, auditMetaDO.getAuditStatus());;
        if (auditMetaDO.getCreateTime() != null) {
            wrapper.apply("DATE_FORMAT( create_time, '%Y-%m-%d' ) = " + "DATE_FORMAT( '" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(auditMetaDO.getCreateTime()) + "' ,'%Y-%m-%d' )");
        }
        if (auditMetaDO.getUpdateTime() != null) {
            wrapper.apply("DATE_FORMAT( update_time, '%Y-%m-%d' ) = " + "DATE_FORMAT( '" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(auditMetaDO.getUpdateTime()) + "' ,'%Y-%m-%d' )");
        }
        return count(wrapper);
    }
    private long countAuditedTotal(AuditMetaDO auditMetaDO) {
        LambdaQueryWrapper<AuditMetaDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtil.isNotBlank(auditMetaDO.getAuditId()), AuditMetaDO::getAuditId, auditMetaDO.getAuditId())
                .like(StringUtil.isNotBlank(auditMetaDO.getPushMan()), AuditMetaDO::getPushMan, auditMetaDO.getPushMan())
                .like(StringUtil.isNotBlank(auditMetaDO.getAuditor()), AuditMetaDO::getAuditor, auditMetaDO.getAuditor())
                .eq(auditMetaDO.getStatus() != null, AuditMetaDO::getStatus, 1)
                .or()
                .eq(auditMetaDO.getStatus() != null, AuditMetaDO::getStatus, 0)
                .eq(auditMetaDO.getAuditStatus() != null, AuditMetaDO::getAuditStatus, auditMetaDO.getAuditStatus());;
        if (auditMetaDO.getCreateTime() != null) {
            wrapper.apply("DATE_FORMAT( create_time, '%Y-%m-%d' ) = " + "DATE_FORMAT( '" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(auditMetaDO.getCreateTime()) + "' ,'%Y-%m-%d' )");
        }
        if (auditMetaDO.getUpdateTime() != null) {
            wrapper.apply("DATE_FORMAT( update_time, '%Y-%m-%d' ) = " + "DATE_FORMAT( '" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(auditMetaDO.getUpdateTime()) + "' ,'%Y-%m-%d' )");
        }
        return count(wrapper);
    }

    @Override
    public boolean updateStatus(List<String> ids, boolean isPass, String suggest) {
        // 若驳回，必须填写驳回意见
        if (!isPass) {
            if (StringUtil.isBlank(suggest)) {
                return false;
            }
        }
        List<AuditMetaDO> auditMetas = auditMetaMapper.selectBatchIds(ids);
        for (AuditMetaDO auditMeta : auditMetas) {
            // 获取状态，若不为-1，则不允许修改；
            if (auditMeta.getStatus() != -1) {
                return false;
            }
            // 审批状态为不为0，不允许驳回
            if (auditMeta.getAuditStatus() != 0 && !isPass) {
                return false;
            }
            auditMeta.setSuggest(suggest).setAuditStatus(2).setStatus(isPass? 1: 0);
        }
        log.info("数据{}通过人工审核", auditMetas);
        // 创建返回给腾讯的信息对象
        for (String id : ids) {
            TencentAuditResultVO tencentAuditResultVO = new TencentAuditResultVO().setAuditId(id).setMsg("通过").setClassification(1)
                    .setSign(EncryptUtil.SHA256HMAC(id, configCache.getAuditSecret()));
        }

        // TODO 给腾讯返回信息 调用接口
        return this.updateBatchById(auditMetas);
    }

    @Override
    @Transactional
    public boolean save(AuditMetaRequest auditMetaRequest) {
        AuditMetaDO auditMetaDO = new AuditMetaDO();
        BeanUtil.vo2DO(auditMetaRequest, auditMetaDO);
        // TODO 调用敏感词审核的接口 object -> json -> object
        // 插入审核元数据
        boolean auditMetaFlag = auditMetaMapper.insert(auditMetaDO) == 1;
        // 设置电影外键，并保存电影数据
        auditMetaRequest.getMovies().forEach(e -> {
            e.setAuditId(auditMetaDO.getAuditId());
        });
        boolean movieFlag = movieService.saveBatch(auditMetaRequest.getMovies());
        // 设置图片外键，合并所有图片，保存数据
        ArrayList<PictureDO> pictures = new ArrayList<>();
        auditMetaRequest.getMovies().forEach(movie -> movie.getPictures().forEach(url -> pictures.add(new PictureDO().setMovieId(movie.getId()).setUrl(url))));
        boolean pictureFlag = pictureService.saveBatch(pictures);
        return auditMetaFlag && movieFlag && pictureFlag;
    }
}
