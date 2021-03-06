package com.study.devmaker.controller;


import com.study.devmaker.dto.*;
import com.study.devmaker.exception.DMakerException;
import com.study.devmaker.service.DmakerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DMakerController {

    private final DmakerService dmakerService;

    @GetMapping("/get-devList")
    public List<DeveloperDto> getAllEmployedDevelopers(){
        //GET /devs HTTP/1.1 요청이 오면 이게 응답함
        log.info("GET /get-devList HTTP/1.1");
        return dmakerService.getAllEmployedDevelopers();
    }

    @GetMapping("/get-devList/{memberId}")
    public DeveloperDetailDto getDevelopers(@PathVariable final String memberId){
        log.info("GET /devs HTTP/1.1");
        return dmakerService.getDeveloper(memberId);
    }

    @PostMapping("/create-devs")
    public CreateDeveloperDto.Response createDevelopers(
            @Valid @RequestBody final CreateDeveloperDto.Request request
            ){
        log.info("POST /create-devs HTTP/1.1");
        log.info("request : {}", request);
        return dmakerService.createDeveloper(request);
    }

    @PutMapping("/update-dev/{memberId}")
    public DeveloperDetailDto updateDeveloper(
            @PathVariable final String memberId,
            @Valid @RequestBody final EditDeveloperDto.Request request
    ){
        log.info("request : {}", request);
        DeveloperDetailDto response =dmakerService.updateDeveloper(memberId, request);
        log.info("response : {}", response);
        return response;
    }

    @DeleteMapping("/delete-dev/{memberId}")
    public DeveloperDetailDto deleteDeveloper(@PathVariable final String memberId){
        return dmakerService.deleteDeveloper(memberId);
    }

}
