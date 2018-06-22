package com.tzy.common.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: lgz
 * @Date: 2018/4/27 11:19
 * @Description:
 */
public class PasswordUtil {

    private final static String ALGORITHM_NAME = "MD5";
    private final static Integer HASH_ITERATIONS = 1;

    /**
     * 生成password和salt
     *
     * @param userPassword 前端传入password
     * @return 加密后的password和salt
     */
    public static Map<String, String> password(String userPassword) {
        String salt = RandomStringUtils.randomAlphabetic(32);
        SimpleHash md5 = new SimpleHash(ALGORITHM_NAME, userPassword, salt, HASH_ITERATIONS);
        String newPassword = md5.toString();
        Map<String, String> map = new HashMap<>();
        map.put("salt", salt);
        map.put("password", newPassword);
        return map;
    }

    /**
     * 验证密码
     *
     * @param userPassword 前端传入password
     * @param dbPassword   数据库password
     * @param dbSalt       数据库salt
     * @return 是否验证成功
     */
    public static boolean genPassword(String userPassword, String dbPassword, String dbSalt) {
        SimpleHash md5 = new SimpleHash(ALGORITHM_NAME, userPassword, dbSalt, HASH_ITERATIONS);
        if (md5.toString().equals(dbPassword)) {
            return true;
        }
        return false;
    }

    public static String md5(String source){
        StringBuffer sb = new StringBuffer(32);
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(source.getBytes("utf-8"));
            for (int i = 0; i < array.length; i++) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).toLowerCase().substring(1, 3));
            }
        } catch (Exception e) {
            return null;
        }
        return sb.toString();
    }
}
