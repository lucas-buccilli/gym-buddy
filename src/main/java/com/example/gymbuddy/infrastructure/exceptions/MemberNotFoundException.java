package com.example.gymbuddy.infrastructure.exceptions;

public class MemberNotFoundException extends RuntimeException{
    private final int id;
    public MemberNotFoundException(int id){
        super("Member Not Found: id = " + id);
        this.id = id;
    }
}
