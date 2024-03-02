package com.relatech.rest.quarkus;

import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;

import jakarta.annotation.Priority;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.ws.rs.PathParam;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@ExecutionTime
@Priority(999)
@Interceptor
@Slf4j
public class ExecutionTimeInterceptor {

	@SneakyThrows
	@AroundInvoke
    public Object logInvocation(InvocationContext context) {
		final StopWatch watch = StopWatch.createStarted();
		final Object target = context.getTarget();
		final String name = context.getMethod().getName();
				
		final Optional<Parameter> idParameter = Arrays
			.stream(context.getMethod().getParameters())
			.filter(_p -> _p.isAnnotationPresent(PathParam.class))
			.filter(_p -> _p.getType().equals(Long.class))
			.findFirst();		

		try {
            return context.proceed();
        } catch (Exception e) {
            //non aggiungere il log.error in quanto le eccezioni vengono già loggate dal io.quarkus.vertx.http.runtime.QuarkusErrorHandler
            throw e;
        } finally {
        	watch.stop();
        	
        	final String clazz = StringUtils.remove(target.getClass().getSimpleName(), "_Subclass");
        	
        	if(idParameter.isPresent()) {
        		final Object[] parameters = context.getParameters();
        		
            	log.info(
        			"[TIME] {}.{}({}) => {}ms", 
        			clazz, name, Arrays.toString(parameters), watch.getTime(TimeUnit.MILLISECONDS)
    			);        		        		
        	} else {
            	log.info(
        			"[TIME] {}.{} => {}ms", 
        			clazz, name, watch.getTime(TimeUnit.MILLISECONDS)
    			);        		
        	}        	
		}
    }	
}
