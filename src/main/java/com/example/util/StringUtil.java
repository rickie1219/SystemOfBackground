package com.example.util;

import java.util.UUID;

public class StringUtil {
    /**
     * 生成32位uuid
     * @return
     */
    public static String getUuid(){
        //转化为String对象
        String uuid = UUID.randomUUID().toString();
        //因为UUID本身为32位只是生成时多了“-”，所以将它们去点就可
        uuid = uuid.replace("-", "");
        return uuid;
    }

    public static Boolean isEmptyString(String str) {
        if (str == null || "".equals(str.trim())) {
            return true;
        } else {
            return false;
        }
    }
}
