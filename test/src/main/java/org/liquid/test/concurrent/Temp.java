package org.liquid.test.concurrent;

/**
 * @author linckye 2018-07-29
 */
public class Temp {

    private static volatile Object temp;

    public static <T> void set(T temp) {
        Temp.temp = temp;
    }

    public static <T> T get() {
        return (T) temp;
    }

}
