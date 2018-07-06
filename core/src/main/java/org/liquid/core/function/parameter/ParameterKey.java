package org.liquid.core.function.parameter;

import org.liquid.common.annotation.NotBlank;
import org.liquid.common.util.Blank;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 对参数key进行封装，可考虑以后进行池化缓存。
 *
 * @author _Chf
 * @since 07/05/2018
 */
public final class ParameterKey<T> {

    private static final AtomicInteger ID_GENERATOR = new AtomicInteger(0);

    private int id;

    private String name;

    private ParameterKey(String name) {
        this.name = name;
        this.id = ID_GENERATOR.getAndDecrement();
    }

    public static <T> ParameterKey<T> valueOf(@NotBlank String name) {
        if (Blank.isNullOrEmpty(name)) {
            throw new NullPointerException("name");
        }
        return new ParameterKey<>(name);
    }

    public int id() {
        return this.id;
    }

    public String name() {
        return this.name;
    }
}
