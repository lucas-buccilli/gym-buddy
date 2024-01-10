package com.example.gymbuddy.infrastructure.exceptions;

public class MachineNotFoundException extends RuntimeException{
    public final String name;

    public MachineNotFoundException(String name) {
        super("Machine Not Found: name = " + name);
        this.name = name;
    }
}
