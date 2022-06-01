package com.example.TestingServer.service;

import com.example.TestingServer.entity.Answer;
import com.example.TestingServer.entity.Question;
import com.example.TestingServer.entity.Topic;
import com.example.TestingServer.exceptions.AnswerNotFoundException;
import com.example.TestingServer.exceptions.QuestionNotFoundException;
import com.example.TestingServer.payload.request.dto.AnswerDTO;
import com.example.TestingServer.payload.request.dto.QuestionDTO;
import com.example.TestingServer.payload.request.dto.TopicDTO;
import com.example.TestingServer.repository.AnswerRepository;
import com.example.TestingServer.repository.QuestionRepository;
import com.example.TestingServer.repository.TopicRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final ModelMapper modelMapper;
    private final TopicRepository topicRepository;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository, AnswerRepository answerRepository, ModelMapper modelMapper, TopicRepository topicRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.modelMapper = modelMapper;
        this.topicRepository = topicRepository;
    }

    @Override
    @Transactional
    public QuestionDTO create(QuestionDTO questionDTO) throws Exception {
        Question question = getQuestion(questionDTO);

        Question questionCreated = questionRepository.save(question);

        return modelMapper.map(questionCreated, QuestionDTO.class);
    }

    @Override
    public List<Question> createAll(List<QuestionDTO> questionsDTO) throws Exception {
        if (!questionsDTO.isEmpty()){
            List<Question> questions = new ArrayList<>();

            for (QuestionDTO questionDTO: questionsDTO)
                questions.add( getQuestion(questionDTO) );

            return questionRepository.saveAll(questions);
        } else {
            return null;
        }
    }

    @Override
    public QuestionDTO update(QuestionDTO questionDTO, Long questionId) throws Exception {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new QuestionNotFoundException("Question not found"));
        question.setName(questionDTO.getName());

        Topic topic = getTopic(questionDTO.getTopic());
        question.setTopic(topic);

        for (AnswerDTO answerDTO: questionDTO.getAnswers()) {
            if(answerDTO.getId() == null){
                question.addComment( modelMapper.map(answerDTO, Answer.class) );
            } else{
                Answer answerUpdate = question.getAnswers().stream()
                        .filter(answer -> answer.getId() == answerDTO.getId()).findFirst()
                        .orElseThrow(() -> new AnswerNotFoundException("Answer not found"));
                modelMapper.map(answerDTO,answerUpdate);
            }
        }

        Question questionUpdated = questionRepository.save(question);

        return modelMapper.map(questionUpdated, QuestionDTO.class);
    }

    @Override
    public void delete(Long questionId) throws Exception {
        questionRepository.deleteById(questionId);
    }

    @Override
    public QuestionDTO getQuestionById(Long questionId) throws Exception {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new QuestionNotFoundException("Question not found"));
        return modelMapper.map(question, QuestionDTO.class);
    }

    @Override
    public List<QuestionDTO> getAllQuestions(Long topicId) throws Exception {
        List<Question> questions = questionRepository.getQuestionsByTopicId(topicId);
        return questions.stream()
                .map(mapper -> modelMapper.map(mapper, QuestionDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<Question> getQuestions(List<Long> questionsId) throws Exception {
        List<Question> questions = null;

        try {
            questions = questionRepository.findAllById(questionsId);
        } catch (Exception e){
            throw new QuestionNotFoundException("Question not found: " + e.getMessage());
        }

        return questions;
    }

    private Question getQuestion(QuestionDTO questionDTO){
        Question question = new Question();
        question.setName(questionDTO.getName());

        Topic topic = getTopic(questionDTO.getTopic());
        question.setTopic(topic);

        for (AnswerDTO answerDTO: questionDTO.getAnswers()) {
            question.addComment(modelMapper.map(answerDTO, Answer.class));
        }

        return question;
    }

    private Topic getTopic(TopicDTO topicDTO){
        Topic topic;

        if (topicDTO.getId() == null){
            Topic topicNew = new Topic();
            topicNew.setName(topicDTO.getName());
            topic = topicRepository.save(topicNew);
        } else{
            if (topicDTO.getName() != null){
                Topic topicNew = modelMapper.map(topicDTO, Topic.class);
                topic = topicRepository.save(topicNew);
            } else {
                topic = topicRepository.getById(topicDTO.getId());
            }
        }

        return topic;
    }
}
