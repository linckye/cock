package org.liquid.common.callback;

import org.liquid.common.annotation.Nullable;

/**
 * 回调基础接口
 *
 * @author _Chf
 * @since 07/05/2018
 * @param <C> 执行方法的具体实例
 * @param <R> 方法执行结果
 */
public interface Callback<C, R> {

    /**
     * 方法执行成功回调
     *
     * @param call 执行方法的具体实例
     * @param result 方法执行结果
     */
    void onSucceed(C call, @Nullable R result);

    /**
     * 方法执行成功失败
     *
     * @param call 执行方法的具体实例
     * @param result 方法执行结果
     * @param throwable 方法执行过程中发生的异常
     */
    void onFailed(C call, @Nullable R result, @Nullable Throwable throwable);
}
