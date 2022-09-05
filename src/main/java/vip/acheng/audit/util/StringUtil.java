package vip.acheng.audit.util;

/**
 * @author 清风徐来
 * @since 2022/8/30 10:30
 */
public class StringUtil {

    public static boolean isBlank(String target) {
        return "".equals(target) || target == null;
    }

    public static boolean isNotBlank(String taget) {
        return !isBlank(taget);
    }

}
