package com.study.devmaker.dto;

import com.study.devmaker.entity.Developer;
import com.study.devmaker.type.DeveloperLevel;
import com.study.devmaker.type.DeveloperSkillType;
import lombok.*;
import org.hibernate.validator.constraints.Range;

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
        private int experienceYears;

        @NotNull
        @Size(min=6, max=20, message="이름은 6~20byte 이내로 작성해주세요")
        private String name;

        @NotNull
        @Size(min=3, max=50, message="아이디는 3~50byte 이내로 작성해주세요")
        private String memberId;

        @Min(18)
        private int age;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    public static class Response{
        private DeveloperLevel developerLevel;
        private DeveloperSkillType developerSkillType;
        private int experienceYears;

        private String memberId;

        public static Response fromEntity(@NonNull Developer dev){
            return Response.builder().
                    developerLevel(dev.getDeveloperLevel()).
                    developerSkillType(dev.getDeveloperSkillType()).
                    experienceYears(dev.getExperienceYears()).
                    memberId(dev.getMemberId()).
                    build();
        }
    }

}
