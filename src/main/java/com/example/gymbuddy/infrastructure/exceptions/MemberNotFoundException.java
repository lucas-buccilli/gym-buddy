package com.example.gymbuddy.infrastructure.exceptions;

public class MemberNotFoundException extends RuntimeException{
    public MemberNotFoundException(int id){
        super("Member Not Found: id = " + id);
    }
    public MemberNotFoundException(String authId){
        super("Member Not Found: authId " + authId);
    }
}
