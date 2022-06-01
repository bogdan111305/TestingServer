package com.example.TestingServer.service;

import com.example.TestingServer.entity.Answer;
import com.example.TestingServer.entity.Question;
import com.example.TestingServer.exceptions.AnswerNotFoundException;
import com.example.TestingServer.exceptions.QuestionNotFoundException;
import com.example.TestingServer.payload.request.dto.AnswerDTO;
import com.example.TestingServer.repository.AnswerRepository;
import com.example.TestingServer.repository.QuestionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnswerServiceImpl implements AnswerService{

    private final AnswerRepository answerRepository;

    private final QuestionRepository questionRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public AnswerServiceImpl(AnswerRepository answerRepository, QuestionRepository questionRepository, ModelMapper modelMapper) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public AnswerDTO create(AnswerDTO answerDTO, Long questionId) throws Exception {
        Question question = questionRepository.getQuestionsById(questionId).orElseThrow(() -> new QuestionNotFoundException("Question not found"));

        Answer answer = new Answer();
        answer.setName(answerDTO.getName());
        answer.setIsTrue(answerDTO.getIsTrue());
        answer.setQuestion(question);

        return modelMapper.map(answerRepository.save(answer), AnswerDTO.class);
    }

    @Override
    public AnswerDTO update(AnswerDTO answerDTO, Long answerId) throws Exception {
        Answer answer = answerRepository.getAnswerById(answerId).orElseThrow(() -> new AnswerNotFoundException("Answer not found"));

        answer.setName(answerDTO.getName());
        answer.setIsTrue(answerDTO.getIsTrue());

        answerRepository.save(answer);

        return modelMapper.map(answer, AnswerDTO.class);
    }

    @Override
    public void delete(Long answerId) {
        answerRepository.deleteById(answerId);
    }

    @Override
    public AnswerDTO getAnswerById(Long answerId) {
        Answer answer = answerRepository.getAnswerById(answerId)
                .orElseThrow(() -> new AnswerNotFoundException("Answer not found"));

        return modelMapper.map(answer, AnswerDTO.class);
    }

}
