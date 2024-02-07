package com.example.gymbuddy.implementation.patchers;

import com.example.gymbuddy.infrastructure.models.dtos.MemberDto;

import java.lang.reflect.Field;

public class MemberPatcher {
    private MemberPatcher() {}
    public static MemberDto patchMember(MemberDto existingMemberDto, MemberDto partialMemberDto) throws IllegalAccessException {
        Class<?> memberDtoClass = MemberDto.class;
        Field[] memberFields = memberDtoClass.getDeclaredFields();
        for (Field field : memberFields) {
            field.setAccessible(true);
            Object value = field.get(partialMemberDto);
            if (value != null) {
               field.set(existingMemberDto, value);
            }
            field.setAccessible(false);
        }
        return existingMemberDto;
    }
}
