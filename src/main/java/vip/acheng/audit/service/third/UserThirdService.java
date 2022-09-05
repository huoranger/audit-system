package vip.acheng.audit.service.third;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import vip.acheng.audit.core.annotation.ThirdURL;

/**
 * @author 清风徐来
 * @since 2022/9/2 9:34
 */
@Service
public class UserThirdService {

    public static final ThreadLocal<String> USERNAME = new ThreadLocal<>();

    @ThirdURL(value = "http://127.0.0.1:8080", METHOD = HttpMethod.POST)
    public boolean login(String username, String password) {
        return true;
    }

}
