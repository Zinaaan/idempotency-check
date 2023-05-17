package com.components.idempotence.aops;

import com.components.idempotence.entities.Idempotence;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lzn
 * @date 2023/01/10 16:19
 * @description idempotence aop
 */
@Aspect
@Component
public class IdempotenceSupportAdvice {

    @Autowired
    private Idempotence idempotence;

    @Pointcut("@annotation(com.components.idempotence.annotations.IdempotenceRequired)")
    public void controllerPointCut() {

    }

    @Around(value = "controllerPointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 从HTTP header中获取幂等号idempotenceId
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        String requestHeader = request.getHeader("xxx");
        String idempotenceId = requestHeader.substring(5, 10);

        boolean existed = idempotence.check(idempotenceId);
        if (existed) {
            // 两种处理方式：
            // 1. 查询order，并且返回；
            // 2. 返回duplication operation Exception
        }
        boolean isSaveSucceed = idempotence.saveIfAbsent(idempotenceId);
        if(isSaveSucceed){
            System.out.println("successfully stored idempotenceId into redis");
        }

        return joinPoint.proceed();
    }
}
