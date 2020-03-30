package com.mask.maskdemoserver.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

/**
 * @Author: marico.lv
 * @CreateTime: 2020-03-14 09:33
 * @Description:
 */
public class CommonUtils {

    private static final char[] CHARR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890~!@#$%^&*.?-+=_"
            .toCharArray();
    private static final String PASSWORD_REGEX = "^(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-Z\\W_!@#$%^&*`~()-+=]+$)(?![a-z0-9]+$)(?![a-z\\W_!@#$%^&*`~()-+=]+$)(?![0-9\\W_!@#$%^&*`~()-+=]+$)[a-zA-Z0-9\\W_!@#$%^&*`~()-+=]{8,16}$";
    private static final String NO_CHINESE_REGEX = "^[^\\u4e00-\\u9fa5]+$";
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);
    private static final Pattern NO_CHINESE_PATTERN = Pattern.compile(NO_CHINESE_REGEX);

    // md5加密
    public static String encryptToMD5(String str) throws NoSuchAlgorithmException {

        // 确定计算方法
        MessageDigest md5;
        md5 = MessageDigest.getInstance("MD5");
        md5.update(str.getBytes());
        return new BigInteger(1, md5.digest()).toString(16);
    }

    // 时间戳散序列加密
    public static String encryptByTimeStamp() throws NoSuchAlgorithmException {
        StringBuilder from = new StringBuilder(encryptToMD5(System.currentTimeMillis() + ""));
        String[] co = {"_", "-", "^", "~", "@", "#", "%", "&"};
        Random rand = new Random();
        for (int i = 0; i < 4; i++) {
            int r = rand.nextInt(8);
            int s = rand.nextInt(from.length());
            from.insert(s, co[r]);
        }
        return from.toString();
    }

    // 生成任意位随机散列
    public static String randomString(int n) {
        StringBuilder sb = new StringBuilder();
        ThreadLocalRandom r = ThreadLocalRandom.current();
        for (int x = 0; x < n; ++x) {
            sb.append(CHARR[r.nextInt(CHARR.length)]);
        }
        return sb.toString();
    }
}