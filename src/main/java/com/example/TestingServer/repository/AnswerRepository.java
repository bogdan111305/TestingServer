package com.example.TestingServer.repository;

import com.example.TestingServer.entity.Answer;
import com.example.TestingServer.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long>, JpaSpecificationExecutor<Answer> {
    Optional<Answer> getAnswerById(Long answerId);
}