package com.lebrwcd.msmservice.utils;/**
 * @author lebrwcd
 * @date 2022/5/14
 * @note
 */

import java.text.DecimalFormat;
import java.util.Random;

/**
 * ClassName RandomUtils
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2022/5/14
 */
public class RandomUtils {

    private static final DecimalFormat fourdf = new DecimalFormat("0000");

    public static String getFourBitRandom() {
        Random random = new Random();
        return fourdf.format(random.nextInt(10000));

    }
}
