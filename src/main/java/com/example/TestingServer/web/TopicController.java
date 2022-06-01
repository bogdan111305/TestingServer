package com.example.TestingServer.web;

import com.example.TestingServer.payload.request.dto.AnswerDTO;
import com.example.TestingServer.payload.request.dto.TopicDTO;
import com.example.TestingServer.payload.request.dto.transfer.Exist;
import com.example.TestingServer.payload.request.dto.transfer.New;
import com.example.TestingServer.service.AnswerService;
import com.example.TestingServer.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/topic")
@CrossOrigin
public class TopicController {

    private final TopicService topicService;

    @Autowired
    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @PostMapping()
    public TopicDTO createTopic(@Validated(New.class) @RequestBody TopicDTO answerDTO) throws Exception {
        return topicService.create(answerDTO);
    }

    @PutMapping("/{topicId}")
    public TopicDTO updateTopic(@Validated(Exist.class) @RequestBody TopicDTO answerDTO, @PathVariable Long topicId) throws Exception {
        return topicService.update(answerDTO, topicId);
    }

    @DeleteMapping("/{topicId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteGroup(@PathVariable Long topicId) throws Exception {
        topicService.delete(topicId);
    }

    @GetMapping("/{topicId}")
    public TopicDTO getTopicById(@PathVariable Long topicId) throws Exception{
        return topicService.getTopicById(topicId);
    }

    @GetMapping()
    public List<TopicDTO> getAll() throws Exception{
        return topicService.getAll();
    }
}