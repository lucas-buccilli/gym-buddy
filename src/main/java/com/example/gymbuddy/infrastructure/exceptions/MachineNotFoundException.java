package com.example.gymbuddy.infrastructure.exceptions;

public class MachineNotFoundException extends RuntimeException{

    public MachineNotFoundException(String name) {
        super("Machine Not Found: name = " + name);
    }
    public MachineNotFoundException(int id) {
        super("Machine Not Found: id = " + id);
    }
}
