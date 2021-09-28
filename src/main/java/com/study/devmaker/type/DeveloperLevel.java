package com.study.devmaker.type;

import com.study.devmaker.exception.DMakerErrorCode;
import com.study.devmaker.exception.DMakerException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Function;

import static com.study.devmaker.contant.DMakerConstant.MAX_JUNIOR_EXPERIENCE_YEAR;
import static com.study.devmaker.contant.DMakerConstant.MIN_SENIOR_EXPERIENCE_YEAR;

@AllArgsConstructor
@Getter
public enum DeveloperLevel {
    NEW("신입",years -> years==0),
    JUNIOR("주니어",years->0<years&&years<=MAX_JUNIOR_EXPERIENCE_YEAR),
    SENIOR("시니어",years->MIN_SENIOR_EXPERIENCE_YEAR<=years&&years<=50);

    private final String description;
    private final Function<Integer, Boolean> validateFuction;

    public void validateExperiencedYear(int year){
        if(!validateFuction.apply(year)){
            throw new DMakerException(DMakerErrorCode.LEVEL_EXPERIMENT_YEAR_NOT_MATCHED);
        }
    }
}
