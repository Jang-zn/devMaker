package com.study.devmaker.service;

import com.study.devmaker.DevMakerApplication;
import com.study.devmaker.dto.CreateDeveloperDto;
import com.study.devmaker.dto.DeveloperDetailDto;
import com.study.devmaker.dto.DeveloperDto;
import com.study.devmaker.entity.Developer;
import com.study.devmaker.exception.DMakerErrorCode;
import com.study.devmaker.exception.DMakerException;
import com.study.devmaker.repository.DevRepository;
import com.study.devmaker.repository.RetiredDevRepository;
import com.study.devmaker.type.DeveloperLevel;
import com.study.devmaker.type.DeveloperSkillType;
import com.study.devmaker.type.StatusCode;
import org.apache.tomcat.util.http.fileupload.MultipartStream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static com.study.devmaker.contant.DMakerConstant.MAX_JUNIOR_EXPERIENCE_YEAR;
import static com.study.devmaker.contant.DMakerConstant.MIN_SENIOR_EXPERIENCE_YEAR;
import static com.study.devmaker.exception.DMakerErrorCode.LEVEL_EXPERIMENT_YEAR_NOT_MATCHED;
import static com.study.devmaker.type.DeveloperLevel.*;
import static com.study.devmaker.type.DeveloperSkillType.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DmakerServiceTest {
    @Mock
    private DevRepository devRepository;

    @Mock
    private RetiredDevRepository retiredDevRepository;

    @InjectMocks
    DmakerService dmakerService;

    private final Developer defaultDev = Developer.builder()
                        .developerLevel(SENIOR)
                        .developerSkillType(FULLSTACK)
                        .experienceYears(15)
                        .memberId("zn2309")
                        .name("J")
                        .age(35)
                        .statusCode(StatusCode.EMPLOYED)
                        .build();

    private CreateDeveloperDto.Request getCreateRequest(
            DeveloperLevel developerLevel, DeveloperSkillType developerSkillType, int experiencedYears
    ){
            return CreateDeveloperDto.Request.builder()
                .developerLevel(developerLevel)
                .developerSkillType(developerSkillType)
                .experienceYears(experiencedYears)
                .memberId("zn2309")
                .name("JangTest")
                .age(30)
                .build();
    }

    @Test
    public void test01(){
        given(devRepository.findByMemberId(anyString())).willReturn(Optional.of(defaultDev));

        DeveloperDetailDto developerDetailDto = dmakerService.getDeveloper("memberId");
        assertEquals(SENIOR, developerDetailDto.getDeveloperLevel());
        assertEquals(FULLSTACK, developerDetailDto.getDeveloperSkillType());
        assertEquals(15, developerDetailDto.getExperienceYears());
    }

    @Test
    void createDevTest_Success(){
        //given
        given(devRepository.findByMemberId(anyString()))
                .willReturn(Optional.empty());
        //return??? ?????? mocking(refactoring)
        given(devRepository.save(any()))
                .willReturn(defaultDev);
        //when
        CreateDeveloperDto.Response developer = dmakerService.createDeveloper(getCreateRequest(SENIOR, FRONTEND, MIN_SENIOR_EXPERIENCE_YEAR));

        //then
        //mocking??? ???????????? ????????? ???????????? ???????????? ???????????? ????????? ?????? ??????
        ArgumentCaptor<Developer> argumentCaptor = ArgumentCaptor.forClass(Developer.class);
        verify(devRepository,times(1)).save(argumentCaptor.capture());
        Developer checkDev = argumentCaptor.getValue();
        assertEquals(SENIOR, checkDev.getDeveloperLevel());
        assertEquals(MIN_SENIOR_EXPERIENCE_YEAR, checkDev.getExperienceYears());
        assertEquals(FRONTEND, checkDev.getDeveloperSkillType());
    }

    @Test
    void createDevTest_Fail(){
        //given
        given(devRepository.findByMemberId(anyString()))
                .willReturn(Optional.of(defaultDev));
        //when
        //then
        DMakerException dMakerException = assertThrows(DMakerException.class, ()->{
            dmakerService.createDeveloper(getCreateRequest(SENIOR, FULLSTACK, MIN_SENIOR_EXPERIENCE_YEAR));
        });

        assertEquals(DMakerErrorCode.DUPLICATED_ID, dMakerException.getDMakerErrorCode());
    }

    @Test
    void validateLevelAndExp_Test() {
        //given

        //when
        //then
        DMakerException dMakerException = assertThrows(DMakerException.class, ()->{
            dmakerService.createDeveloper(
                    getCreateRequest(SENIOR, FULLSTACK, MIN_SENIOR_EXPERIENCE_YEAR-1
                    )
            );
        });
        assertEquals(DMakerErrorCode.LEVEL_EXPERIMENT_YEAR_NOT_MATCHED, dMakerException.getDMakerErrorCode());

        dMakerException = assertThrows(DMakerException.class, ()->{
            dmakerService.createDeveloper(
                    getCreateRequest(
                            JUNIOR, FULLSTACK, MAX_JUNIOR_EXPERIENCE_YEAR+1
                    )
            );
        });
        assertEquals(
                DMakerErrorCode.LEVEL_EXPERIMENT_YEAR_NOT_MATCHED, dMakerException.getDMakerErrorCode()
        );
    }
}