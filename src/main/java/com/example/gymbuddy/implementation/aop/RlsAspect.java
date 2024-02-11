package com.example.gymbuddy.implementation.aop;

import com.example.gymbuddy.implementation.services.UserAuthDetailsService;
import com.example.gymbuddy.infrastructure.daos.IMemberDao;
import com.example.gymbuddy.infrastructure.exceptions.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;

import java.awt.desktop.PreferencesEvent;
import java.util.Arrays;
import java.util.Optional;


@RequiredArgsConstructor
@Aspect
public class RlsAspect {
    private final IMemberDao memberDao;

    /**
     * Verifies that the authenticated user can perform an operation on the member.
     * This only executes before methods annotated with {@link  EnforceRls#memberIdParameterName()}
     * @param joinPoint The calling methods join point
     */
    @Before("@within(org.springframework.web.bind.annotation.RestController) && execution(public * *(..)) && within(com.example.gymbuddy.*)")
    public void enforceRls(JoinPoint joinPoint) {
        var userDetails = UserAuthDetailsService.getUserAuthDetails();
        var authenticatedMember = memberDao.findByAuthId(userDetails.authId()).orElseThrow(() -> new MemberNotFoundException(userDetails.authId()));
        var memberId = getMemberIdFromMethod(joinPoint);

        if (userDetails.admin()) {
            return;
        }

        if (memberId.isEmpty() || authenticatedMember.getId().equals(memberId.get())) {
            return;
        }

        throw new AccessDeniedException("User does not have permission");
    }

    /**
     * Gets the integer value from the calling method based off the parameter name stored in {@link  EnforceRls#memberIdParameterName()}
     * @param joinPoint the calling methods join point
     * @return member id
     */
    private Optional<Integer> getMemberIdFromMethod(JoinPoint joinPoint) {
        var methodSignature = (MethodSignature) joinPoint.getSignature();
        var annotation = methodSignature.getMethod().getAnnotation(EnforceRls.class);
        if(annotation == null) throw new IllegalStateException("Method {" + methodSignature.getDeclaringTypeName() + "} is missing EnforceRls annotation");
        if(annotation.noMemberParameter()) return Optional.empty();
        var parameterNameFromAnnotation = annotation.memberIdParameterName();
        var parameterIndex = Arrays.stream(methodSignature.getParameterNames()).toList().indexOf(parameterNameFromAnnotation);
        if (parameterIndex < 0) {
            throw new IllegalStateException("Method {" + methodSignature.getDeclaringTypeName() + "} does not have parameter with name {" + parameterNameFromAnnotation + "}");
        } else {
            var parameterValue = joinPoint.getArgs()[parameterIndex];
            if (parameterValue instanceof Integer) {
                return Optional.of((Integer) parameterValue);
            } else {
                throw new IllegalStateException("Method {" + methodSignature.getDeclaringTypeName() + "} parameter {" + parameterNameFromAnnotation + "} does not have a type of Integer");
            }
        }
    }
}

