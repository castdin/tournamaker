package com.example.cstde037.tournamaker;

/**
 * Created by cstde037 on 03/12/2015.
 */
/* A utility class for generic mathematical computations required to organize playoff tournaments */
public class MathUtil {
    public static boolean isPowerOfTwo(int num) {
        return (num & -num) == num; //http://stackoverflow.com/questions/9146855/determine-if-num-is-a-power-of-two-in-java
    }

    public static int roundDownToPowerOfTwo(int num) {
        return Integer.highestOneBit(num);
    }

    public static int logTwo(int num) {
        return (int)(Math.log(num) / Math.log(2));
    }
}
