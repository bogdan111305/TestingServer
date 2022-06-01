package com.example.TestingServer.service;

import com.example.TestingServer.entity.Question;
import com.example.TestingServer.entity.Variant;
import com.example.TestingServer.payload.request.dto.QuestionDTO;

import java.util.List;
import java.util.Optional;

public interface QuestionService {
    QuestionDTO create(QuestionDTO questionDTO) throws Exception;
    List<Question> createAll(List<QuestionDTO> questionsDTO) throws Exception;
    QuestionDTO update(QuestionDTO questionDTO, Long questionId) throws Exception;
    void delete(Long questionId) throws Exception;
    QuestionDTO getQuestionById(Long questionId) throws Exception;
    List<QuestionDTO> getAllQuestions(Long topicId) throws Exception;
    List<Question> getQuestions(List<Long> questionsId) throws Exception;
}
