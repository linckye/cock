package org.liquid.core.function.java;

import org.liquid.common.callback.Callback;
import org.liquid.common.command.Commands;
import org.liquid.common.command.JavaOptions;
import org.liquid.common.util.Blank;
import org.liquid.common.util.ProcessUtils;
import org.liquid.core.function.Function;
import org.liquid.core.function.FunctionContext;
import org.liquid.core.function.parameter.Parameter;
import org.liquid.core.function.parameter.ParameterKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author _Chf
 * @since 07/05/2018
 */
public class JavaFunction implements Function {

    private final Logger logger = LoggerFactory.getLogger(JavaFunction.class);

    private static final ParameterKey<String> JAR_LOCATION = ParameterKey.valueOf("jar-location");

    @Override
    public void call(FunctionContext functionContext) {
        Parameter<String> jarLocationParam = functionContext.param(JAR_LOCATION);
        String jarLocation = jarLocationParam.get();
        if (Blank.isNullOrEmpty(jarLocation)) {
            //TODO 封装异常
            throw new RuntimeException("没有传入jar包文件路径参数");
        }
        List<String> command = Commands.newJavaCommandBuilder()
                .withOption(JavaOptions.JAR).withParam(jarLocation)
                .build();
        ProcessUtils.start(command, new Callback<Process, String>() {
            @Override
            public void onSucceed(Process call, String result) {
            }

            @Override
            public void onFailed(Process call, String result, Throwable throwable) {
                logger.error("执行jar包发生错误", throwable);
            }
        });
    }
}
