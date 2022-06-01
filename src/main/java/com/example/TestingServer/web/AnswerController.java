package com.example.TestingServer.web;

import com.example.TestingServer.payload.request.dto.AnswerDTO;
import com.example.TestingServer.payload.request.dto.GroupDTO;
import com.example.TestingServer.payload.request.dto.transfer.Exist;
import com.example.TestingServer.payload.request.dto.transfer.New;
import com.example.TestingServer.service.AnswerService;
import com.example.TestingServer.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/answer")
@CrossOrigin
public class AnswerController {

    private final AnswerService answerService;

    @Autowired
    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping("/{questionId}")
    public AnswerDTO createAnswer(@Validated(New.class) @RequestBody AnswerDTO answerDTO, @PathVariable Long questionId) throws Exception {
        return answerService.create(answerDTO, questionId);
    }

    @PutMapping("/{answerId}")
    public AnswerDTO updateAnswer(@Validated(Exist.class) @RequestBody AnswerDTO answerDTO, @PathVariable Long answerId) throws Exception {
        return answerService.update(answerDTO, answerId);
    }

    @DeleteMapping("/{answerId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAnswer(@PathVariable Long answerId) throws Exception {
        answerService.delete(answerId);
    }

    @GetMapping("/{answerId}")
    public AnswerDTO getAnswerById(@PathVariable Long answerId) throws Exception{
        return answerService.getAnswerById(answerId);
    }
}