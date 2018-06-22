package com.tzy.common.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;

public class MathUtil {

    public static final DecimalFormat FORMAT = new DecimalFormat("#.######");


    /**
     * 数字格式化
     *
     * @param number 数字
     * @return 数字
     */
    public static final double fmt(double number) {
        return Double.parseDouble(FORMAT.format(number));
    }

    /**
     * 加法
     *
     * @param scale  基数
     * @param augend 被加数
     * @return 结果
     */
    public static final double add(double scale, double augend) {
        BigDecimal d1 = new BigDecimal(Double.toString(scale));
        BigDecimal d2 = new BigDecimal(Double.toString(augend));
        BigDecimal d3 = d1.add(d2);
        return Double.parseDouble(FORMAT.format(d3.doubleValue()));
    }

    /**
     * @param [math]
     * @return double
     * @date 2018/5/21 11:54
     * @describe 多参数相加
     */
    public static final double addMax(double... math) {
        BigDecimal zero = BigDecimal.ZERO;
        for (int i = 0; i < math.length; i++) {
            BigDecimal d1 = new BigDecimal(Double.toString(math[i]));
            zero = zero.add(d1);
        }
        return Double.parseDouble(FORMAT.format(zero.doubleValue()));
    }

    /**
     * 减法
     *
     * @param scale   基数
     * @param minuend 被减数
     * @return
     */
    public static final double sub(double scale, double minuend) {
        BigDecimal d1 = new BigDecimal(Double.toString(scale));
        BigDecimal d2 = new BigDecimal(Double.toString(minuend));
        BigDecimal d3 = d1.subtract(d2);
        return Double.parseDouble(FORMAT.format(d3.doubleValue()));
    }

    /**
     * 乘法
     *
     * @param scale        基数
     * @param multiplicand 被乘数
     * @return 结果
     */
    public static final double mul(double scale, double multiplicand) {
        BigDecimal d1 = new BigDecimal(Double.toString(scale));
        BigDecimal d2 = new BigDecimal(Double.toString(multiplicand));
        BigDecimal d3 = d1.multiply(d2);
        return Double.parseDouble(FORMAT.format(d3.doubleValue()));
    }

    /**
     * 除法
     *
     * @param scale    基数
     * @param dividend 被除数
     * @return 结果
     */
    public static final double div(double scale, double dividend) {
        BigDecimal d1 = new BigDecimal(Double.toString(scale));
        BigDecimal d2 = new BigDecimal(Double.toString(dividend));
        BigDecimal d3 = d1.divide(d2, MathContext.DECIMAL128);
        return Double.parseDouble(FORMAT.format(d3.doubleValue()));
    }
}
