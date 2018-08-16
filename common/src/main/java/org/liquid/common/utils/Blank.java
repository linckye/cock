package org.liquid.common.utils;

import java.util.Collection;
import java.util.Map;

/**
 * 判空工具类
 *
 * @author linckye 2018-05-16
 */
public class Blank {

    public static boolean isNull(Object object) {
        return object == null;
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || "".equals(str);
    }

    public static boolean isNullOrEmpty(String... strs) {
        if (strs == null) return true;
        for (String str : strs) {
            if (isNullOrEmpty(str)) return true;
        }
        return false;
    }

    public static boolean isNullOrEmpty(Object[] objectArray) {
        return objectArray == null || objectArray.length == 0;
    }

    public static boolean isNullOrEmpty(Collection collection) {
        return collection == null || collection.size() == 0;
    }

    public static boolean isNullOrEmpty(Map map) {
        return map == null || map.size() == 0;
    }

    public static boolean isNullOrEmpty(Iterable iterable) {
        if (iterable == null) return true;
        for (Object item : iterable) {
            if (item == null) return true;
        }
        return false;
    }

    public static boolean isNullOrEmpty(int[] intArray) {
        return intArray == null || intArray.length == 0;
    }

    public static boolean isNullOrEmpty(long[] longArray) {
        return longArray == null || longArray.length == 0;
    }

    public static boolean isNullOrEmpty(float[] floatArray) {
        return floatArray == null || floatArray.length == 0;
    }

    public static boolean isNullOrEmpty(double[] doubleArray) {
        return doubleArray == null || doubleArray.length == 0;
    }

    public static boolean isNullOrEmpty(short[] shortArray) {
        return shortArray == null || shortArray.length == 0;
    }

    public static boolean isNullOrEmpty(boolean[] booleanArray) {
        return booleanArray == null || booleanArray.length == 0;
    }

    public static boolean isNullOrEmpty(char[] charArray) {
        return charArray == null || charArray.length == 0;
    }

    public static boolean isNullOrEmpty(byte[] byteArray) {
        return byteArray == null || byteArray.length == 0;
    }

    public static boolean containsNullOrNoneElements(Object[] objectArray) {
        if (isNullOrEmpty(objectArray)) return true;
        for (Object object : objectArray) {
            if (object == null) return true;
        }
        return false;
    }

}
