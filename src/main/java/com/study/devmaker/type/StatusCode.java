package com.study.devmaker.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusCode {
    EMPLOYED("고용"),
    RETIRED("퇴직");
    private final String description;
}
