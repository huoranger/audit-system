package vip.acheng.audit.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @author 清风徐来
 * @since 2022/8/31 9:36
 */
@Data
@TableName("audit_meta")
@Accessors(chain = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AuditMetaDO {
    @TableId(type = IdType.INPUT)
    private String auditId;
    private String pushMan;
    private String auditType;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    private Integer auditStatus;
    private Integer status;
    private Integer safeLevel;
    private String auditor;
    private String autoAuditor;
    private boolean backList;
    private boolean sensitiveWord;
    private String suggest;
    private boolean passAutoAudit;

    @TableField(exist = false)
    @JsonProperty("movie_list")
    private List<MovieDO> movieDOList;
}
