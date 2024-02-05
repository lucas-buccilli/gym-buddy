package com.example.gymbuddy.implementation.configurations;

import com.example.gymbuddy.infrastructure.models.dtos.MemberDto;
import com.example.gymbuddy.infrastructure.models.requests.MemberRequests;
import org.modelmapper.ModelMapper;
import org.modelmapper.record.RecordModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        var modelMapper =  new ModelMapper().registerModule(new RecordModule());
        modelMapper.typeMap(MemberRequests.AddRequest.class, MemberDto.class)
                .addMappings(mp -> mp.skip(MemberDto::setId));
        modelMapper.typeMap(MemberRequests.ReplaceRequest.class, MemberDto.class)
                .addMappings(mp -> mp.skip(MemberDto::setId));
        return modelMapper;
    }
}
