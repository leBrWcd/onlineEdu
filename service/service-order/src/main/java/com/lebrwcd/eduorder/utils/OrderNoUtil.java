package com.lebrwcd.eduorder.utils;/**
 * @author lebrwcd
 * @date 2022/5/30
 * @note
 */

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * ClassName OrderNoUtil
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2022/5/30
 */
public class OrderNoUtil {

    public static String RandomOrder() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String newDate = sdf.format(new Date());
        String result = "";
        Random random = new Random();
        for(int i = 0;i<3;i++) {
            result+=random.nextInt(10);
        }
        return newDate+result;
    }

}
