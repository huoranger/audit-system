package vip.acheng.audit.util;

import org.springframework.beans.BeanUtils;

/**
 * @author 清风徐来
 * @since 2022/8/31 11:09
 */
public class BeanUtil {
    public static Object vo2DO(Object source, Object target) {
        BeanUtils.copyProperties(source, target);
        return target;
    }

    public static Object vo2DO(Object source, Object target, String[] ignores) {
        BeanUtils.copyProperties(source, target, ignores);
        return target;
    }
}
