package vip.acheng.audit.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 清风徐来
 * @since 2022/8/30 15:50
 */
@TableName("movie")
@Data
public class MovieDO {
    @TableId(type = IdType.AUTO)
    @JsonProperty("id")
    @JsonIgnore
    private Integer id;

    @JsonProperty("con_id")
    @JsonIgnore
    private String conId;

    @TableField("con_title")
    @JsonProperty("con_title")
    private String title;

    @TableField("con_subtitle")
    @JsonProperty("con_subtitle")
    private String subtitle;

    @TableField(exist = false)
    @JsonProperty("con_pics")
    private List<String> pictures;

    @JsonProperty("channel_en_name")
    private String channelEnName;

    @JsonProperty("channel_name")
    private String channelName;

    @JsonIgnore
    @JsonProperty("audit_id")
    private String auditId;
}
