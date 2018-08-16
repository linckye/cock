package org.liquid.test.concurrent;

import lombok.ToString;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 使用 {@link #get()} 获取运行轨迹，并与其他运行轨迹对比，可获得以下信息：
 * <ul>
 *     <li>运行先后顺序</li>
 *     <li>运行线程</li>
 * </ul>
 *
 * @author linckye 2018-07-21
 */
@ToString
public class RunTrace {

    /** 运行线程 id. **/
    private Long runThreadId;

    /** 运行先后顺序计数. **/
    private Long runOrderCount;

    /** 运行先后顺序计数器. **/
    private static final AtomicLong runOrderCounter = new AtomicLong();

    /** 是否运行在其之前. **/
    public boolean isRunBefore(
            @Nonnull RunTrace... runTraces) {
        return Arrays.stream(runTraces)
                .allMatch(runTrace -> this.runOrderCount < runTrace.runOrderCount);
    }

    /** 是否运行在其之后. **/
    public boolean isRunAfter(
            @Nonnull RunTrace... runTraces) {
        return !isRunBefore(runTraces);
    }

    /** 是否运行在同一线程. **/
    public boolean isRunOnTheSameThread(
            @Nonnull RunTrace... runTraces) {
        return Arrays.stream(runTraces)
                .allMatch(runTrace -> this.runThreadId.equals(runTrace.runThreadId));
    }

    /** 是否运行在不同线程. **/
    public boolean isRunOnTheDifferentThread(
            @Nonnull RunTrace... runTraces) {
        return !isRunOnTheSameThread(runTraces);
    }

    /** 获取当前运行轨迹. **/
    public static RunTrace get() {
        RunTrace runTrace = new RunTrace();
        runTrace.runThreadId = Thread.currentThread().getId();
        runTrace.runOrderCount = runOrderCounter.incrementAndGet();
        return runTrace;
    }

    private RunTrace() {}

}
