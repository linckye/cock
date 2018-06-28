package org.liquid.core.function;

/**
 * 一段可运行的函数
 *
 * @author linckye 2018-05-16
 */
public interface Function {

    /**
     * 调用函数
     *
     * @param functionContext 函数上下文
     */
    void call(FunctionContext functionContext);

}
