package com.example.util;

import org.apache.shiro.crypto.hash.Md5Hash;
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

    // 导入包 import org.apache.shiro.crypto.hash.Md5Hash;
    public static void md5Two() {
        String source = "123456";
        Md5Hash hash = new Md5Hash(source);
        System.out.println("使用md5加密后的结果：+"+hash.toString());
        Md5Hash hash2 = new Md5Hash(source,"北京武汉");
        System.out.println("使用md5加密并且加盐后的结果：+"+hash2.toString());
        Md5Hash hash3 = new Md5Hash(source,"北京武汉",2);
        System.out.println("使用md5加密并且加盐并且散列2次后的结果：+"+hash2.toString());
    }

    /*
     * source 需要加密的铭文
     * salt 盐
     * hashInterations 散列次数
     * */
    public static String md5(String source,Object salt,Integer hashInterations){
        return new Md5Hash(source,salt,hashInterations).toString();
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
