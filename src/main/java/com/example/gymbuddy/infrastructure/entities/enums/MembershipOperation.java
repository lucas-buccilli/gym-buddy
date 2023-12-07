package com.example.gymbuddy.infrastructure.entities.enums;

public enum MembershipOperation {
    ACTIVATED("A"),
    DEACTIVATED("D");
    private final String code;

    private MembershipOperation(String code){
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
