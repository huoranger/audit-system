package vip.acheng.audit.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author 清风徐来
 * @since 2022/8/31 9:41
 */
@TableName("picture")
@Data
@Accessors(chain = true)
public class PictureDO {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String url;
    private Integer movieId;
}
