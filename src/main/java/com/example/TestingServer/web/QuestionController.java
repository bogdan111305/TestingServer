package com.example.TestingServer.web;

import com.example.TestingServer.payload.request.dto.QuestionDTO;
import com.example.TestingServer.payload.request.dto.transfer.Exist;
import com.example.TestingServer.payload.request.dto.transfer.New;
import com.example.TestingServer.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/question")
@CrossOrigin
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService){
        this.questionService = questionService;
    }

    @PostMapping("")
    public QuestionDTO createQuestion(@Validated(New.class) @RequestBody QuestionDTO questionDTO) throws Exception {
        return questionService.create(questionDTO);
    }

    @PutMapping("/{questionId}")
    public QuestionDTO updateQuestion(@PathVariable(name = "questionId") Long questionId, @Validated(Exist.class) @RequestBody QuestionDTO questionDTO) throws Exception {
        return questionService.update(questionDTO, questionId);
    }

    @DeleteMapping("/{questionId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteQuestion(@PathVariable("questionId")Long questionId) throws Exception {
        questionService.delete(questionId);
    }

    @GetMapping("/{questionId}")
    public QuestionDTO getQuestion(@PathVariable(name = "questionId") Long questionId) throws Exception{
        return questionService.getQuestionById(questionId);
    }

    @GetMapping("")
    public List<QuestionDTO> getAllQuestions(@RequestParam(name = "topicId", required = false) Long topicId) throws Exception{
        return questionService.getAllQuestions(topicId);
    }
}
