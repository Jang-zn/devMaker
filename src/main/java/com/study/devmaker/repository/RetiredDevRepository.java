package com.study.devmaker.repository;

import com.study.devmaker.entity.Developer;
import com.study.devmaker.entity.RetiredDeveloper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RetiredDevRepository extends JpaRepository<RetiredDeveloper, Long> {
    //Developer가 있을수도 / 없을수도 있으니까 리턴형식을 Optional로 줌
    //findBy+컬럼명으로 자동으로 조회하는 SpringDataJPA 제공기능
    Optional<RetiredDeveloper> findByMemberId(String memberId);
}
