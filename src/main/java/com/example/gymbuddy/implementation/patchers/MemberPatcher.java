package com.example.gymbuddy.implementation.patchers;

import com.example.gymbuddy.infrastructure.models.dtos.MemberDto;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class MemberPatcher {
    public static void patchMember(MemberDto existingMemberDao, MemberDto partialMemberDao) throws IllegalAccessException {
        Class<?> memberDtoClass = MemberDto.class;
        Field[] memberFields = memberDtoClass.getDeclaredFields();
        for (Field field : memberFields) {
            field.setAccessible(true);
            Object value = field.get(partialMemberDao);
            if (value != null) {
               field.set(existingMemberDao, value);
            }
            field.setAccessible(false);
        }
    }
}
