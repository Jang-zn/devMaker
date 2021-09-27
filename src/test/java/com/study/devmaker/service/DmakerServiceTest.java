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

import static com.study.devmaker.type.DeveloperLevel.*;
import static com.study.devmaker.type.DeveloperSkillType.*;
import static org.junit.jupiter.api.Assertions.*;
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

    private final CreateDeveloperDto.Request request = CreateDeveloperDto.Request.builder()
            .developerLevel(SENIOR)
            .developerSkillType(FRONTEND)
            .experienceYears(12)
            .memberId("zn2309")
            .name("JangTest")
            .age(30)
            .build();

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
        //when
        CreateDeveloperDto.Response developer = dmakerService.createDeveloper(request);

        //then
        //mocking된 객체에서 메소드 실행할때 매개변수 캡쳐해서 가지고 있는 객체
        ArgumentCaptor<Developer> argumentCaptor = ArgumentCaptor.forClass(Developer.class);
        verify(devRepository,times(1)).save(argumentCaptor.capture());
        Developer checkDev = argumentCaptor.getValue();
        assertEquals(SENIOR, checkDev.getDeveloperLevel());
        assertEquals(12, checkDev.getExperienceYears());
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
            dmakerService.createDeveloper(request);
        });

        assertEquals(DMakerErrorCode.DUPLICATED_ID, dMakerException.getDMakerErrorCode());
    }
}