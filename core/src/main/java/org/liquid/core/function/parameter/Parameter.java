package org.liquid.core.function.parameter;

/**
 * @author _Chf
 * @since 07/05/2018
 */
public interface Parameter<T> {

    /**
     * @return 该参数对应的key
     */
    ParameterKey<T> key();

    /**
     * @return 获取值
     */
    T get();

    /**
     * 设置值
     *
     * @param value 值
     */
    void set(T value);
}
