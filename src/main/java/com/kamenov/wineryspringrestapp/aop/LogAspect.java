package com.kamenov.wineryspringrestapp.aop;

import com.kamenov.wineryspringrestapp.service.LogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {
    private final LogService logService;

    public LogAspect(LogService logService) {
        this.logService = logService;
    }

    @Pointcut("execution(*com.kamenov.wineryspringrestApp.web.WineController.details(..))")
    public void followDetails(){

    }
    @After("followDetails()")
    public void afterAdvice(JoinPoint joinPoint){

        Object[] args = joinPoint.getArgs();
        Long articleId = (Long) args[0];
        String action = joinPoint.getSignature().getName();

        logService.createLog(action, articleId);
    }
}
