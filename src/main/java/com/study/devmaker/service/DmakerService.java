package com.study.devmaker.service;

import com.study.devmaker.entity.Developer;
import com.study.devmaker.repository.DevRepository;
import com.study.devmaker.type.DeveloperLevel;
import com.study.devmaker.type.DeveloperSkillType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


//RequiredArgsConstructor 붙여주면 Repository class final 선언으로 생성시마다 주입되게 설정 가능
//AutoWired / Inject 안써도 된다. -> Annotation종속을 느슨하게 해서 별개 Test같은거 가능하게 함
@Service
@RequiredArgsConstructor
public class DmakerService {
    private final DevRepository devRepository;

    @Transactional
    public void createDeveloper(){
        Developer developer =  Developer.builder()
                .developerLevel(DeveloperLevel.NEW)
                .developerSkillType(DeveloperSkillType.BACKEND)
                .experienceYear(1)
                .memberId("zn2309")
                .age(30)
                .name("Jang")
                .build();
        devRepository.save(developer);
    }
}
