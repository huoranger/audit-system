package vip.acheng.audit.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 清风徐来
 * @since 2022/9/2 10:08
 */
public class JsonUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static String object2String(Object target) throws JsonProcessingException {
        return mapper.writeValueAsString(target);
    }

    public static Map string2Map(String target) throws JsonProcessingException {
        return mapper.readValue(target, HashMap.class);
    }
}
