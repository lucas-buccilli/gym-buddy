package com.example.gymbuddy.implementation.configurations;

import com.example.gymbuddy.implementation.aop.RlsAspect;
import com.example.gymbuddy.infrastructure.daos.IMemberDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@Configuration
public class AspectJConfig {

    @Bean
    public RlsAspect aspects(IMemberDao memberDao) {
        return new RlsAspect(memberDao);
    }
}
