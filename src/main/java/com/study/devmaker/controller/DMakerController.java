package com.study.devmaker.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
public class DMakerController {
    @GetMapping("/devs")
    public List<String> getAllDevelopers(){
        //GET /devs HTTP/1.1 요청이 오면 이게 응답함
        log.info("GET /devs HTTP/1.1");
        return Arrays.asList("1","2","3");
    }

}
