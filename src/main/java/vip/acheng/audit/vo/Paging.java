package vip.acheng.audit.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author 清风徐来
 * @since 2022/8/30 15:57
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Paging<T> {

    private Integer current;
    private Integer size;
    private Long total;
    private List<T> data;
}
