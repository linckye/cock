package org.liquid.services.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.liquid.client.model.Response;
import org.springframework.stereotype.Component;

import static org.liquid.client.model.Codes.*;
import static org.liquid.common.utils.Blank.*;

/**
 * @author linckye 2018-08-16
 */
@Component
@Aspect
@Slf4j
public class ServiceServerAspect {

    @Pointcut("execution(* org.liquid..*.*ServiceServer.*(..))")
    private void serverPointCut() {}

    @Around("ServiceServerAspect.serverPointCut()")
    @SuppressWarnings("unchecked")
    public Object handlerMethod(ProceedingJoinPoint proceedingJoinPoint) throws Exception {

        // check args
        if (containsNullOrNoneElements(proceedingJoinPoint.getArgs()))
            return ((Class<? extends Response>) ((MethodSignature) proceedingJoinPoint.getSignature()).getReturnType())
                    .newInstance()
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
            return ((Class<? extends Response>) ((MethodSignature) proceedingJoinPoint.getSignature()).getReturnType())
                    .newInstance()
                    .code(UNEXPECTED_ERROR)
                    .message(t.getMessage());
        }
    }

}
