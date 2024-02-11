package com.example.gymbuddy.implementation.aop;

import org.aspectj.lang.JoinPoint;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation that is used to trigger {@link  com.example.gymbuddy.implementation.aop.RlsAspect#enforceRls(JoinPoint)}}
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface EnforceRls {
    String memberIdParameterName() default "";
    boolean noMemberParameter() default false;
}
