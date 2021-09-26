package com.study.devmaker.dto;


import com.study.devmaker.exception.DMakerErrorCode;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DmakerResponse {
    private DMakerErrorCode dMakerErrorCode;
    private String errorMessage;

}
