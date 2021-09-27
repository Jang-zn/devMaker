package com.study.devmaker.exception;


import com.study.devmaker.dto.DmakerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
@Slf4j
public class DMakerExceptionHandler {
    @ResponseStatus(value= HttpStatus.CONFLICT)
    @ExceptionHandler(DMakerException.class)
    public DmakerResponse handleException(DMakerException e, HttpServletRequest request){
        log.error("errorCode : {}, url : {}, message : {} ",
                e.getDMakerErrorCode(), request.getRequestURI(), e.getErrorMessage());
        return DmakerResponse.builder()
                .errorMessage(e.getErrorMessage())
                .dMakerErrorCode(e.getDMakerErrorCode())
                .build();
    }


    @ExceptionHandler(value={
            HttpRequestMethodNotSupportedException.class,
            MethodArgumentNotValidException.class
    })
    public DmakerResponse handleBadRequest(Exception e, HttpServletRequest request){
        log.error("url : {}, message : {} ",
                request.getRequestURI(), e.getMessage());
        return DmakerResponse.builder()
                .errorMessage(DMakerErrorCode.INVALID_REQUEST.getMessage())
                .dMakerErrorCode(DMakerErrorCode.INVALID_REQUEST)
                .build();
    }


    @ExceptionHandler(Exception.class)
    public DmakerResponse handleException(Exception e, HttpServletRequest request){
        log.error("url : {}, message : {} ",
                request.getRequestURI(), e.getMessage());
        return DmakerResponse.builder()
                .errorMessage(DMakerErrorCode.INTERNAL_SERVER_ERROR.getMessage())
                .dMakerErrorCode(DMakerErrorCode.INTERNAL_SERVER_ERROR)
                .build();
    }
}
