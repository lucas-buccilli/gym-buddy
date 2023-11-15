package com.example.gymbuddy.infrastructure.exceptions;

public class MachineNotFoundException extends RuntimeException{

    public MachineNotFoundException() {
        super("Machine Not Found");
    }
}
