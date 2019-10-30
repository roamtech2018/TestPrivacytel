package com.wxf.testprivacytel;

import android.text.TextUtils;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 签名工具
 *
 * @author wxf 2019-07-16
 */
public class SignUtils {
    // 失效时间(秒)
    private static final long INVALID_TIME = 5 * 60;

    /**
     * 获取签名
     *
     * @param map        参数
     * @param corpSecret 密钥
     * @return String
     */
    public static String getSignature(Map<String, ?> map, String corpSecret) {
        // 初始化参数拼接字符串
        StringBuilder keySB = new StringBuilder();
        // 有参数情况
        if (null != map && map.size() > 0) {
            // 把参数放入list
            List<String> list = new ArrayList<>(map.keySet());
            // 按自然排序
            Collections.sort(list);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // 排序后遍历拼接参数
            for (String key : list) {
                Object object = map.get(key);
                if (object != null) {
                    String value;
                    if (object instanceof Date) {
                        value = dateFormat.format((Date) object);
                    } else {
                        value = object.toString();
                    }
                    if (!TextUtils.isEmpty(value)) {
                        keySB.append(key).append("=").append(value).append("&");
                    }
                }
            }
            // 按照要求最后加上corp_secret
            keySB.append("corp_secret=").append(corpSecret);
        }
        return generateValue(keySB.toString());
    }

    private static final char[] hexCode = "0123456789abcdef".toCharArray();

    private static String toHexString(byte[] data) {
        if(data == null) {
            return null;
        }
        StringBuilder r = new StringBuilder(data.length*2);
        for ( byte b : data) {
            r.append(hexCode[(b >> 4) & 0xF]);
            r.append(hexCode[(b & 0xF)]);
        }
        return r.toString();
    }


    private static String generateValue(String param) {
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(param.getBytes());
            byte[] messageDigest = algorithm.digest();
            return toHexString(messageDigest);
        } catch (Exception e) {
            return null;
        }
    }
}
