package vip.acheng.audit.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 清风徐来
 * @since 2022/9/2 9:38
 */
public class AuthUtil {

    /**
     * 私钥
     */
    private static final String SECRET = "1@3$.Ssd$%7^";

    /**
     * 过期时间
     */
    private static final int EXPIRED = 7;

    /**
     * 创建Token
     * @param map 前台传输的用户信息
     * @return Token
     */
    public static String getToken(Map<String, String> map) {
        JWTCreator.Builder builder = JWT.create();

        // 声明过期时间：7天
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, EXPIRED);
        Date expireTime = calendar.getTime();

        // Payload装载数据
        for (Map.Entry<String, String> entry : map.entrySet()) {
            builder.withClaim(entry.getKey(), entry.getValue());
        }
        builder.withExpiresAt(expireTime);

        // Signature配置，同时获取最终生成的Token
        return builder.sign(Algorithm.HMAC256(SECRET));
    }

    /**
     * 验证Token合法性，假如Token被修改，则抛出异常
     * @param token 待验证的Token
     */
    public static void verifyToken(String token) {
        // 验证Token必须使用和创建一样的算法和私钥
        JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
    }

    /**
     * 通过载荷名字获取载荷的值
     * @param token 已验证的Token
     * @param name payload中的key
     * @return payload中的value
     */
    public static Claim getClaimByName(String token, String name){
        return JWT.decode(token).getClaim(name);
    }


}
