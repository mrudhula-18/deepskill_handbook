package com.dn5.week2.springcore;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Aspect that logs method entry/exit and execution time for every method
 * invoked on {@link Car}. Uses {@code System.out} so this module does not
 * need to pull in an additional logging dependency beyond what is specified
 * for this exercise.
 */
@Aspect
@Component
public class LoggingAspect {

    @Around("execution(* com.dn5.week2.springcore.Car.*(..))")
    public Object logAroundCarMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        long start = System.nanoTime();
        System.out.println("[LoggingAspect] Entering " + methodName);
        try {
            Object result = joinPoint.proceed();
            long elapsedMillis = (System.nanoTime() - start) / 1_000_000;
            System.out.println("[LoggingAspect] Exiting " + methodName
                    + " - returned '" + result + "' in " + elapsedMillis + " ms");
            return result;
        } catch (Throwable throwable) {
            long elapsedMillis = (System.nanoTime() - start) / 1_000_000;
            System.out.println("[LoggingAspect] Exception in " + methodName
                    + " after " + elapsedMillis + " ms: " + throwable);
            throw throwable;
        }
    }
}
