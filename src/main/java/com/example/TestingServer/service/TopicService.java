package com.example.TestingServer.service;

import com.example.TestingServer.payload.request.dto.GroupDTO;
import com.example.TestingServer.payload.request.dto.TopicDTO;

import java.util.List;

public interface TopicService {
    TopicDTO getTopicById(Long topicId);

    List<TopicDTO> getAll() throws Exception;

    TopicDTO create(TopicDTO topicDTO) throws Exception;

    TopicDTO update(TopicDTO topicDTO, Long topicId) throws Exception;

    void delete(Long topicId);
}
