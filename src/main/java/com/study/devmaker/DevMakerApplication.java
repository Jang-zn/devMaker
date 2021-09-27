package com.study.devmaker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


//Row 생성 / 수정시점 갱신용 Annotation EnableJpaAudting
@SpringBootApplication
public class DevMakerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DevMakerApplication.class, args);
    }

}
