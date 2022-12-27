package com.ejercicio.mindata.superheroes.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class LoggingAspect {

    private static Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("@annotation(com.ejercicio.mindata.superheroes.logging.LogExecutionTime)")
    public Object measureTime(ProceedingJoinPoint point) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Object objectRuntime = point.proceed();
        stopWatch.stop();
        logger.info("Tiempo estimado de ejecucion del metodo de Controlador " + point.getSignature().getName() + "() es "
                + stopWatch.getTotalTimeMillis() + " ms");
        return objectRuntime;
    }
}
