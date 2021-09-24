package com.study.devmaker.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DMakerErrorCode {
    LEVEL_EXPERIMENT_YEAR_NOT_MATCHED("개발자 레벨과 연차가 맞지 않습니다."),
    NO_DEVELOPER("해당되는 개발자가 없습니다."),
    DUPLICATED_ID("중복되는 ID입니다."),

    //진짜 예상치 못한 서버오류에 대해서 퉁쳐주는 마법의 에러
    INTERNAL_SERVER_ERROR("서버에 오류가 발생했습니다"),

    INVALID_REQUEST("잘못된 요청입니다.")
    ;

    private final String message;

}
