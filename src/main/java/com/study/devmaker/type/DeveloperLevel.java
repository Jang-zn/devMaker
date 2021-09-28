package com.study.devmaker.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.study.devmaker.contant.DMakerConstant.MAX_JUNIOR_EXPERIENCE_YEAR;
import static com.study.devmaker.contant.DMakerConstant.MIN_SENIOR_EXPERIENCE_YEAR;

@AllArgsConstructor
@Getter
public enum DeveloperLevel {
    NEW("신입",0,0),
    JUNIOR("주니어",1,MAX_JUNIOR_EXPERIENCE_YEAR),
    SENIOR("시니어",MIN_SENIOR_EXPERIENCE_YEAR,50);

    private final String description;
    private final int minExperiencedYear;
    private final int maxExperiencedYear;
}
