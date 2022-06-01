package com.example.TestingServer.service;

import com.example.TestingServer.entity.Group;
import com.example.TestingServer.entity.Topic;
import com.example.TestingServer.exceptions.GroupNotFoundException;
import com.example.TestingServer.exceptions.TopicNotFoundException;
import com.example.TestingServer.payload.request.dto.GroupDTO;
import com.example.TestingServer.payload.request.dto.TopicDTO;
import com.example.TestingServer.repository.GroupRepository;
import com.example.TestingServer.repository.TopicRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicServiceImpl implements TopicService{

    private final TopicRepository topicRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public TopicServiceImpl(TopicRepository topicRepository, ModelMapper modelMapper) {
        this.topicRepository = topicRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public TopicDTO getTopicById(Long topicId) {
        Topic topic = topicRepository.findTopicById(topicId).orElseThrow(() -> new TopicNotFoundException("Group not found"));
        return modelMapper.map(topic, TopicDTO.class);
    }

    @Override
    public List<TopicDTO> getAll() throws Exception {
        return topicRepository.findAll().stream().map(topic -> modelMapper.map(topic, TopicDTO.class)).collect(Collectors.toList());
    }

    @Override
    public TopicDTO create(TopicDTO topicDTO) throws Exception {
        Topic topic = new Topic();
        topic.setName(topicDTO.getName());
        topic = topicRepository.save(topic);
        return modelMapper.map(topic, TopicDTO.class);
    }

    @Override
    public TopicDTO update(TopicDTO topicDTO, Long topicId) throws Exception {
        Topic topic = topicRepository.findTopicById(topicId).orElseThrow(() -> new TopicNotFoundException("Group not found"));
        topic.setName(topicDTO.getName());
        topic = topicRepository.save(topic);
        return modelMapper.map(topic, TopicDTO.class);
    }

    @Override
    public void delete(Long topicId) {
        topicRepository.deleteById(topicId);
    }
}
