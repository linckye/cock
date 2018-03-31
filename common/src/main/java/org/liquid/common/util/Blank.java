package org.liquid.common.util;

import java.util.Collection;
import java.util.Map;

/**
 * @author linckye 2018-03-31
 */
public class Blank {

    public boolean isNull(Object object) {
        return object == null;
    }

    public boolean isNullOrEmpty(String string) {
        return string == null || string.length() == 0;
    }

    public boolean isNullOrEmpty(Object[] objectArray) {
        return objectArray == null || objectArray.length == 0;
    }

    public boolean isNullOrEmpty(Collection collection) {
        return collection == null || collection.size() == 0;
    }

    public boolean isNullOrEmpty(Map map) {
        return map == null || map.size() == 0;
    }

    public boolean isNullOrEmpty(Iterable iterable) {
        if (iterable == null) return true;
        for (Object item : iterable) {
            if (item == null) return true;
        }
        return false;
    }

    public boolean isNullOrEmpty(int[] intArray) {
        return intArray == null || intArray.length == 0;
    }

    public boolean isNullOrEmpty(char[] charArray) {
        return charArray == null || charArray.length == 0;
    }

    public boolean isNullOrEmpty(byte[] byteArray) {
        return byteArray == null || byteArray.length == 0;
    }

    public boolean isNullOrEmpty(short[] shortArray) {
        return shortArray == null || shortArray.length == 0;
    }

    public boolean isNullOrEmpty(boolean[] booleanArray) {
        return booleanArray == null || booleanArray.length == 0;
    }

    public boolean isNullOrEmpty(long[] longArray) {
        return longArray == null || longArray.length == 0;
    }

    public boolean isNullOrEmpty(double[] doubleArray) {
        return doubleArray == null || doubleArray.length == 0;
    }

    public boolean isNullOrEmpty(float[] floatArray) {
        return floatArray == null || floatArray.length == 0;
    }

}
