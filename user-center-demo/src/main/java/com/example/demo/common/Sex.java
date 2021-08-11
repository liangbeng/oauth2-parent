package com.example.demo.common;

public enum Sex {
    MAN(1,"男"),
    WOMAN(2,"女");
    Sex(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private Integer code;
    private String desc;


}
