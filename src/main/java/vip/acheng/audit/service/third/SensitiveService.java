package vip.acheng.audit.service.third;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import vip.acheng.audit.core.annotation.ThirdURL;

import java.util.List;

/**
 * @author 清风徐来
 * @since 2022/8/30 10:11
 */
@Service
public class SensitiveService {

    @ThirdURL(value = "http://localhost:80/sensitive", METHOD = HttpMethod.GET)
    public Object sensitiveWordRequest() {
        return null;
    }
}
