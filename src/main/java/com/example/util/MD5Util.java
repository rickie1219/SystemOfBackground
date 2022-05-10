package com.example.util;

import org.springframework.util.DigestUtils;

public class MD5Util {
    /**
     * MD5 方法
     * @param text 明文 账号加密码
     * @param key  密钥 加盐
     * @return 密文
     */
    public static String md5(String text, String key) {
        // 加密后的字符串
        String strBytes = "" + text + key;
        return DigestUtils.md5DigestAsHex(strBytes.getBytes());
    }

    public static boolean verify(String text, String key, String md5) {
        // 根据传入的密钥进行验证
        String md5Text = md5(text, key);
        System.out.println("通过验证MD5加密的密码， === " + md5Text);
        if (md5Text.equalsIgnoreCase(md5)) {
            return true;
        }
        return false;
    }
}
