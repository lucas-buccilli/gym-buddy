package com.example.gymbuddy.infrastructure.exceptions;

import com.example.gymbuddy.infrastructure.models.daos.Dao;
import com.example.gymbuddy.infrastructure.models.daos.MachineDao;

import java.util.HashMap;

public class AlreadyExistsException extends RuntimeException{
    private final static HashMap<Class<?>, String> NAME_MAP = new HashMap<>();

    static {
        NAME_MAP.put(MachineDao.class, "Machine");
    }

    public AlreadyExistsException(Class<? extends Dao> daoClass, String identifier) {
        super(getNameFromClass(daoClass) + " already exists with identifier: " + identifier);
    }

    private static String getNameFromClass(Class<?> clazz) {
        if (NAME_MAP.containsKey(clazz)) {
            return NAME_MAP.get(clazz);
        }
        throw new IllegalArgumentException("Value " + clazz.getName() + " does not exist");
    }
}
