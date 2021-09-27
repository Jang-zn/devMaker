package com.study.devmaker.controller;

import com.study.devmaker.dto.DeveloperDto;
import com.study.devmaker.service.DmakerService;
import com.study.devmaker.type.DeveloperLevel;
import com.study.devmaker.type.DeveloperSkillType;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;


import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(DMakerController.class)
class DMakerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DmakerService dmakerService;

    protected MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
                                                    MediaType.APPLICATION_JSON.getSubtype(),
                                                    StandardCharsets.UTF_8);

    @Test
    public void getAllDevelopers() throws Exception{
        DeveloperDto newDeveloperDto = DeveloperDto.builder()
                .developerLevel(DeveloperLevel.NEW)
                .developerSkillType(DeveloperSkillType.FULLSTACK)
                .experienceYears(1)
                .memberId("zn2309")
                .build();
        DeveloperDto jrDeveloperDto = DeveloperDto.builder()
                .developerLevel(DeveloperLevel.JUNIOR)
                .developerSkillType(DeveloperSkillType.FULLSTACK)
                .experienceYears(6)
                .memberId("zn2310")
                .build();
        given(dmakerService.getAllEmployedDevelopers()).willReturn((Arrays.asList(newDeveloperDto, jrDeveloperDto)));
        mockMvc.perform(get("/get-devList").contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(
                         jsonPath("$.[0].developerSkillType", is(DeveloperSkillType.FULLSTACK.name()))
                ).andExpect(
                         jsonPath("$.[0].developerLevel", is(DeveloperLevel.NEW.name()))
                ).andExpect(
                         jsonPath("$.[1].developerSkillType", is(DeveloperSkillType.FULLSTACK.name()))
                ).andExpect(
                         jsonPath("$.[1].developerLevel", is(DeveloperLevel.JUNIOR.name()))
                );
    }
}