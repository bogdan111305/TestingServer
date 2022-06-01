package com.example.TestingServer.service;

import com.example.TestingServer.entity.Answer;
import com.example.TestingServer.entity.Question;
import com.example.TestingServer.payload.request.dto.AnswerDTO;

import java.util.List;

public interface AnswerService {
    AnswerDTO create(AnswerDTO answerDTO, Long questionId) throws Exception;

    AnswerDTO update(AnswerDTO answerDTO, Long answerId) throws Exception;

    void delete(Long answerId);

    AnswerDTO getAnswerById(Long answerId);
}
