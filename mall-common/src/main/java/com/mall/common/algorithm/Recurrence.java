package com.mall.common.algorithm;

/**
 * Recurrence
 *
 * @author youfu.wang
 * @date 2023/9/7 15:05
 */
public class Recurrence {
    public static int factorial(int n) {
        if (n == 0) {
            return 1;
        } else {
            return n * factorial(n - 1);
        }
    }

    public static void main(String[] args) {
        System.out.println(factorial(4));
    }

}
