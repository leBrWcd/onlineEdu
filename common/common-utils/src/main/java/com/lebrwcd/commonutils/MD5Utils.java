package com.lebrwcd.commonutils;/**
 * @author lebrwcd
 * @date 2022/5/14
 * @note
 */

/**
 * ClassName MD5Utils
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2022/5/14
 */

/**
 * MD5算法，对输入的密码进行加密，保存到数据库中的密码不能是明文的
 */
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
    public static String getMD5(String password) {
        try {
            // 得到一个信息摘要器
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(password.getBytes());
            StringBuffer buffer = new StringBuffer();
            // 把每一个byte 做一个与运算 0xff;
            for (byte b : result) {
                // 与运算
                int number = b & 0xff;// 加盐
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }
            // 标准的md5加密后的结果
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

}