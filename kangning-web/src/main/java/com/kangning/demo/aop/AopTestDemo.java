package com.kangning.demo.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author 加康宁
 * @version ${Id}
 * @date 2018-07-25 Time: 11:16
 */
@Aspect
@Component
public class AopTestDemo {


    @Pointcut("execution(* com.kangning.demo.controller.*Controller.*(..))")
    public void controllerMonitor(){}

    @Before("@annotation(org.springframework.web.bind.annotation.RequestMapping) && controllerMonitor()")
    public void beforeMethod(JoinPoint joinPoint){
        System.out.println("beforeMethod" + Arrays.toString(joinPoint.getArgs()));
    }

    @After("@annotation(org.springframework.web.bind.annotation.RequestMapping) && controllerMonitor()")
    public void afterMethod(JoinPoint joinPoint){
        System.out.println("afterMethod" + Arrays.toString(joinPoint.getArgs()));
    }

    @Around("@annotation(org.springframework.web.bind.annotation.RequestMapping) && controllerMonitor()")
    public Object aroundMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("aroundMethod before");
        Object obj = proceedingJoinPoint.proceed();
        System.out.println("aroundMethod" + obj.toString());
        return obj;
    }

}

