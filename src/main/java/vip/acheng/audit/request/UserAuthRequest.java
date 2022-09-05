package vip.acheng.audit.request;

import jdk.nashorn.internal.runtime.regexp.joni.ast.StringNode;
import lombok.Data;

/**
 * @author 清风徐来
 * @since 2022/9/2 10:35
 */
@Data
public class UserAuthRequest {
    private String username;
    private String password;
}
