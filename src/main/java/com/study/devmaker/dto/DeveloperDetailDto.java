package com.study.devmaker.dto;

import com.study.devmaker.entity.Developer;
import com.study.devmaker.type.DeveloperLevel;
import com.study.devmaker.type.DeveloperSkillType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DeveloperDetailDto {
    private DeveloperLevel developerLevel;
    private DeveloperSkillType developerSkillType;
    private int experienceYears;

    private String memberId;
    private String name;
    private int age;

    public static DeveloperDetailDto fromEntity(Developer dev){
        return DeveloperDetailDto.builder().
                developerLevel(dev.getDeveloperLevel()).
                developerSkillType(dev.getDeveloperSkillType()).
                experienceYears(dev.getExperienceYears()).
                memberId(dev.getMemberId()).
                name(dev.getName()).
                age(dev.getAge()).
                build();
    }
}
