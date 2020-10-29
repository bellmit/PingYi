package com.example.upc.common;

public enum ReturnStateEnum {

    STATUS("STATUS"),

    MESSAGE("MESSAGE"),

    data("data"),

    SUCCESS("SUCCESS"),

    WARNING("WARNING");

    private String value;

    private ReturnStateEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
