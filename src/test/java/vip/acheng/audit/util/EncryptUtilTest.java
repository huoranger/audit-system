package vip.acheng.audit.util;

import org.junit.Test;

/**
 * @author 清风徐来
 * @since 2022/9/1 16:57
 */
public class EncryptUtilTest {

    @Test
    public void testSHA256HMAC() {
        String sign = EncryptUtil.SHA256HMAC("100000000001", "e54df970bc4d3c2ab62cf1dfa4b78559");
        System.out.println(sign);
    }

}
