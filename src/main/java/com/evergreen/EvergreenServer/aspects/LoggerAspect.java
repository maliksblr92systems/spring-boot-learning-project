package com.evergreen.EvergreenServer.aspects;

import java.util.Arrays;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggerAspect {


    // @Before("execution(* com.evergreen.EvergreenServer..*(..)) &&
    // !within(com.evergreen.EvergreenServer.filters.JwtAuthenticationFilter)")
    // public void logStart() {
    // System.out.println("=========================================");
    // System.out.println("=========================================");
    // System.out.println("Execution started.");
    // System.out.println("=========================================");
    // System.out.println("=========================================");

    // }


    @Around("execution(* com.evergreen.EvergreenServer.services.*.*(..))")
    public Object aroundControllers(ProceedingJoinPoint pjp) throws Throwable {
        String className = pjp.getTarget().getClass().getSimpleName();
        String methodName = pjp.getSignature().getName();
        Object[] args = pjp.getArgs();

        System.out.println("===============================================");
        System.out.println("🔹 Entering Method: " + className + "." + methodName + "(..)");
        System.out.println("🔹 Arguments: " + (args.length > 0 ? Arrays.toString(args) : "No arguments"));

        long startTime = System.currentTimeMillis();
        Object result;

        try {
            result = pjp.proceed(); // Execute the actual method
        } catch (Throwable ex) {
            System.out.println("❌ Exception in " + className + "." + methodName + ": " + ex.getMessage());
            throw ex; // propagate exception
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        System.out.println("✅ Method Completed: " + className + "." + methodName + "(..)");
        System.out.println("🔹 Execution Time: " + duration + " ms");
        System.out.println("🔹 Return Value: " + (result != null ? result : "void/null"));
        System.out.println("===============================================");

        return result;
    }


}
