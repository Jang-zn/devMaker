package com.study.devmaker.service;

import com.study.devmaker.dto.CreateDeveloperDto;
import com.study.devmaker.dto.DeveloperDto;
import com.study.devmaker.entity.Developer;
import com.study.devmaker.type.DeveloperLevel;
import com.study.devmaker.type.DeveloperSkillType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DmakerServiceTest {

    @Autowired
    DmakerService dmakerService;

    @Test
    public void test01(){
        dmakerService.createDeveloper(CreateDeveloperDto.Request.builder()
                .developerLevel(DeveloperLevel.SENIOR)
                .developerSkillType(DeveloperSkillType.FULLSTACK)
                .experienceYears(15)
                .age(40)
                .name("J")
                .memberId("zn2309")
                .build());
        List<DeveloperDto> allEmployedDevelopers = dmakerService.getAllEmployedDevelopers();
        System.out.println("-------------------");
        System.out.println(allEmployedDevelopers);
        System.out.println("-------------------");
    }
}