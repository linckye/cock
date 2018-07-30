package org.liquid.common.util;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.liquid.common.callback.Callback;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 创建子进程工具类
 *
 * @author _Chf
 * @since 07/05/2018
 */
public abstract class ProcessUtils {

    /** TODO 可以考虑统一下线程池的管理 */
    private static final Executor DEFAULT_PROCESS_EXECUTOR = Executors.newCachedThreadPool();

    /**
     * 启动新的子进程
     *
     * @param command 开启子进程的参数
     * @param outputHandler 子进程的输出流回调方法
     */
    public static void start(@NonNull List<String> command,
                             @NonNull OutputHandler outputHandler) {
        start(command, outputHandler, DEFAULT_PROCESS_EXECUTOR);
    }

    /**
     * 指定线程池启动新的子进程
     *
     * @param command 开启子进程的参数
     * @param outputHandler 子进程的输出流回调方法，对每一行进行回调
     * @param executor 实际启动的线程池
     */
    public static void start(@NonNull List<String> command,
                             @NonNull OutputHandler outputHandler,
                             @NonNull Executor executor) {
        Objects.requireNonNull(command);
        Objects.requireNonNull(outputHandler);
        Objects.requireNonNull(executor);
        executor.execute(() -> {
            ProcessBuilder builder = new ProcessBuilder(command);
            builder.redirectErrorStream(true);
            Process process = null;
            try {
                process = builder.start();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    outputHandler.onSucceed(process, line);
                }
            } catch (IOException e) {
                outputHandler.onFailed(process, null, e);
            }
        });
    }

    /**
     * 子进程输出的每一行回调接口
     */
    public interface OutputHandler extends Callback<Process, String> {}

}
