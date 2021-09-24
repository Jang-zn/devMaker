package com.study.devmaker.dto;

import com.study.devmaker.type.DeveloperLevel;
import com.study.devmaker.type.DeveloperSkillType;
import jdk.jfr.MemoryAddress;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateDeveloperDto {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    public static class Request{
        @NotNull
        private DeveloperLevel developerLevel;

        @NotNull
        private DeveloperSkillType developerSkillType;

        @NotNull
        @Range(min=0, max=20)
        private int experimentYear;

        @NotNull
        @Size(min=6, max=20, message="이름은 6~20byte 이내로 작성해주세요")
        private String name;

        @NotNull
        @Size(min=3, max=50, message="아이디는 3~50byte 이내로 작성해주세요")
        private String memberId;

        @Min(18)
        private int age;
    }

    public static class Response{
        private DeveloperLevel developerLevel;
        private DeveloperSkillType developerSkillType;
        private int experimentYear;

        private String memberId;
    }

}
