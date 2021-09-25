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
public class DeveloperDto {

    private DeveloperLevel developerLevel;
    private DeveloperSkillType developerSkillType;
    private int experienceYears;

    private String memberId;

    public static DeveloperDto fromEntity(Developer dev){
        return DeveloperDto.builder().
                developerLevel(dev.getDeveloperLevel()).
                developerSkillType(dev.getDeveloperSkillType()).
                experienceYears(dev.getExperienceYears()).
                memberId(dev.getMemberId()).
                build();
    }

}
