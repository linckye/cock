package org.liquid.services.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.liquid.client.model.Response;
import org.springframework.stereotype.Component;

import static org.liquid.client.model.Codes.*;
import static org.liquid.common.utils.Blank.*;

/**
 * 对 {@code .*ServiceServer} 进行增强.
 *
 * @author linckye 2018-08-16
 */
@Component
@Aspect
@Slf4j
public class ServerAspect {

    @Pointcut("execution(* org.liquid..*.*ServiceServer.*(..))")
    private void serverPointCut() {}

    @Around("ServerAspect.serverPointCut()")
    public Object handleError(ProceedingJoinPoint proceedingJoinPoint) {
        // check args
        if (containsNullOrNoneElements(proceedingJoinPoint.getArgs())) return new Response()
                .code(ILLEGAL_ARGUMENT)
                .message("Null request");

        // handle throwable
        try {
            return proceedingJoinPoint.proceed();
        } catch (Throwable t) {
            log.error("Unexpected error method={} args={}",
                    proceedingJoinPoint.getSignature(),
                    proceedingJoinPoint.getArgs(),
                    t
            );
            return new Response()
                    .code(UNEXPECTED_ERROR)
                    .message(t.getMessage());
        }
    }

}
