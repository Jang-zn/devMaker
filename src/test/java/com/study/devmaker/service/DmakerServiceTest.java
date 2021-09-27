package com.study.devmaker.service;

import com.study.devmaker.dto.CreateDeveloperDto;
import com.study.devmaker.dto.DeveloperDetailDto;
import com.study.devmaker.dto.DeveloperDto;
import com.study.devmaker.entity.Developer;
import com.study.devmaker.repository.DevRepository;
import com.study.devmaker.repository.RetiredDevRepository;
import com.study.devmaker.type.DeveloperLevel;
import com.study.devmaker.type.DeveloperSkillType;
import com.study.devmaker.type.StatusCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class DmakerServiceTest {
    @Mock
    private DevRepository devRepository;

    @Mock
    private RetiredDevRepository retiredDevRepository;

    @InjectMocks
    DmakerService dmakerService;

    @Test
    public void test01(){
        given(devRepository.findByMemberId(anyString())).willReturn(Optional.of(Developer.builder()
                        .developerLevel(DeveloperLevel.SENIOR)
                        .developerSkillType(DeveloperSkillType.FULLSTACK)
                        .experienceYears(15)
                        .memberId("zn2309")
                        .name("J")
                        .age(35)
                        .statusCode(StatusCode.EMPLOYED)
                        .build()));

        DeveloperDetailDto developerDetailDto = dmakerService.getDeveloper("memberId");
        assertEquals(DeveloperLevel.SENIOR, developerDetailDto.getDeveloperLevel());
        assertEquals(DeveloperSkillType.FULLSTACK, developerDetailDto.getDeveloperSkillType());
        assertEquals(15, developerDetailDto.getExperienceYears());
    }
}