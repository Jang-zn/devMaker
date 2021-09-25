package com.study.devmaker.controller;


import com.study.devmaker.dto.CreateDeveloperDto;
import com.study.devmaker.dto.DeveloperDetailDto;
import com.study.devmaker.dto.DeveloperDto;
import com.study.devmaker.service.DmakerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class DMakerController {

    private final DmakerService dmakerService;

    @GetMapping("/get-devList")
    public List<DeveloperDto> getAllDevelopers(){
        //GET /devs HTTP/1.1 요청이 오면 이게 응답함
        log.info("GET /get-devList HTTP/1.1");
        return dmakerService.getAllDevelopers();
    }

    @GetMapping("/get-devList/{memberId}")
    public DeveloperDetailDto getDevelopers(@PathVariable String memberId){
        log.info("GET /devs HTTP/1.1");
        return dmakerService.getDeveloper(memberId);
    }

    @PostMapping("/create-devs")
    public CreateDeveloperDto.Response createDevelopers(
            @Valid @RequestBody CreateDeveloperDto.Request request
            ){
        log.info("POST /create-devs HTTP/1.1");
        log.info("request : {}", request);
        return dmakerService.createDeveloper(request);
    }


}
