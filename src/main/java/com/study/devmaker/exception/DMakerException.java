package com.study.devmaker.exception;


import lombok.Getter;

@Getter
public class DMakerException extends RuntimeException{
    private DMakerErrorCode dMakerErrorCode;
    private String errorMessage;

    public DMakerException(DMakerErrorCode errorCode){
        super(errorCode.getMessage());
        this.dMakerErrorCode = errorCode;
        this.errorMessage = errorCode.getMessage();
    }

    public DMakerException(DMakerErrorCode errorCode, String detailMessage){
        super(detailMessage);
        this.dMakerErrorCode = errorCode;
        this.errorMessage = detailMessage;
    }
}
