package com.example.gymbuddy.infrastructure.exceptions;

public class MemberNotFoundException extends RuntimeException{
    public MemberNotFoundException(){
        super("Member Not Found");
    }
}
