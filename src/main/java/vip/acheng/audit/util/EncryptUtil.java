package vip.acheng.audit.util;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * @author 清风徐来
 * @since 2022/9/1 16:18
 */
@Slf4j
public class EncryptUtil {

    /**
     * 将加密后的字节数组转换成字符串
     */
    private static String byteArrayToHexString(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b!=null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1) {
                hs.append('0');
            }
            hs.append(stmp);
        }
        return hs.toString().toLowerCase();
    }

    public static String SHA256HMAC(String message, String secret) {
        String hash = "";
        try {
            Mac sha256 = Mac.getInstance("HmacSHA256");
            //注意统一编码
            SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            sha256.init(secretKey);
            byte[] bytes = sha256.doFinal(message.getBytes(StandardCharsets.UTF_8));
            hash = byteArrayToHexString(bytes);
        } catch (Exception e) {
            log.error("加密出错:{}", e);
        }
        return hash;
    }
}
