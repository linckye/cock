package org.liquid.core.function.parameter;

/**
 * 对参数进行访问
 *
 * @author _Chf
 * @since 07/05/2018
 */
public interface ParameterMap {

    /**
     * 获取参数
     *
     * @param key 参数唯一标识码
     * @param <T> 参数值类型
     * @return 参数实例
     */
    <T> Parameter<T> param(ParameterKey<T> key);

    /**
     * 查看是否含有参数
     *
     * @param key 参数唯一标识码
     * @param <T> 参数值类型
     * @return 是否含有
     */
    <T> boolean hasParam(ParameterKey<T> key);
}
