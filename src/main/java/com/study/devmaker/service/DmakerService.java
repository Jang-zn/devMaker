package com.study.devmaker.service;

import com.study.devmaker.dto.CreateDeveloperDto;
import com.study.devmaker.dto.DeveloperDetailDto;
import com.study.devmaker.dto.DeveloperDto;
import com.study.devmaker.dto.EditDeveloperDto;
import com.study.devmaker.entity.Developer;
import com.study.devmaker.entity.RetiredDeveloper;
import com.study.devmaker.exception.DMakerException;
import com.study.devmaker.repository.DevRepository;
import com.study.devmaker.repository.RetiredDevRepository;
import com.study.devmaker.type.DeveloperLevel;
import com.study.devmaker.type.StatusCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.study.devmaker.exception.DMakerErrorCode.*;


//RequiredArgsConstructor 붙여주면 Repository class final 선언으로 생성시마다 주입되게 설정 가능
//AutoWired / Inject 안써도 된다. -> Annotation종속을 느슨하게 해서 별개 Test같은거 가능하게 함
@Service
@RequiredArgsConstructor
public class DmakerService {
    private final DevRepository devRepository;
    private final RetiredDevRepository retiredDevRepository;

    @Transactional(readOnly = true)
    public CreateDeveloperDto.Response createDeveloper(CreateDeveloperDto.Request request){
        validateCreateDeveloperRequest(request);
            Developer developer = Developer.builder()
                    .developerLevel(request.getDeveloperLevel())
                    .developerSkillType(request.getDeveloperSkillType())
                    .experienceYears(request.getExperienceYears())
                    .memberId(request.getMemberId())
                    .age(request.getAge())
                    .name(request.getName())
                    .statusCode(StatusCode.EMPLOYED)
                    .build();
            devRepository.save(developer);
            return CreateDeveloperDto.Response.fromEntity(developer);
    }

    @Transactional(readOnly = true)
    public List<DeveloperDto> getAllEmployedDevelopers() {
        return devRepository.findDevelopersByStatusCode(StatusCode.EMPLOYED)
                .stream()
                .map(DeveloperDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public DeveloperDetailDto getDeveloper(String memberId) {
        return devRepository.findByMemberId(memberId)
                .map(DeveloperDetailDto::fromEntity)
                .orElseThrow(()->new DMakerException(NO_DEVELOPER));
    }

    @Transactional
    public DeveloperDetailDto updateDeveloper(String memberId, EditDeveloperDto.Request request) {
        validateLevelAndExp(
                request.getDeveloperLevel(),
                request.getExperienceYears()
        );
        Developer developer=devRepository
                .findByMemberId(memberId)
                .orElseThrow(()->new DMakerException(NO_DEVELOPER));

        developer.setDeveloperLevel(request.getDeveloperLevel());
        developer.setDeveloperSkillType(request.getDeveloperSkillType());
        developer.setExperienceYears(request.getExperienceYears());
        //
        return DeveloperDetailDto.fromEntity(developer);
    }

    @Transactional(readOnly = true)
    public DeveloperDetailDto deleteDeveloper(String memberId) {
        //1. Employed->Retired
        Developer developer = devRepository.findByMemberId(memberId)
                .orElseThrow(()->new DMakerException(NO_DEVELOPER));
        developer.setStatusCode(StatusCode.RETIRED);
        //2. RetiredDeveloper Table에 추가
        RetiredDeveloper retiredDeveloper = RetiredDeveloper.builder()
                .memberId(developer.getMemberId())
                .name(developer.getName())
                .build();
        retiredDevRepository.save(retiredDeveloper);
        return DeveloperDetailDto.fromEntity(developer);
    }



    //검증
    //비즈니스 정책에 따른 검증 수행
    private void validateCreateDeveloperRequest(@NonNull CreateDeveloperDto.Request request) {
        validateLevelAndExp(
                request.getDeveloperLevel(),
                request.getExperienceYears()
        );
        //Id중복 검증
        //변수로 받고 if문으로 null인지 아닌지 다시 확인할 필요 없음 (콜백함수, 람다식 이용 java8부터 가능한거임..)
        devRepository.findByMemberId(request.getMemberId()).ifPresent((developer -> {
            throw new DMakerException(DUPLICATED_ID);
        }));

    }


    private void validateLevelAndExp(DeveloperLevel developerLevel, int experimentYear) {
        if(developerLevel ==DeveloperLevel.SENIOR
                && experimentYear <10){
            //정책상 발생하는 Exception은 custom Exception으로 구현해주는게 더 명확함.
            throw new DMakerException(LEVEL_EXPERIMENT_YEAR_NOT_MATCHED);
        }
        if(developerLevel==DeveloperLevel.JUNIOR
                && (4>experimentYear|| experimentYear>10)){
            throw new DMakerException(LEVEL_EXPERIMENT_YEAR_NOT_MATCHED);
        }
        if(developerLevel==DeveloperLevel.NEW&&experimentYear>4){
            throw new DMakerException(LEVEL_EXPERIMENT_YEAR_NOT_MATCHED);
        }
    }


}
