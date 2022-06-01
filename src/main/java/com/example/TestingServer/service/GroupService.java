package com.example.TestingServer.service;

import com.example.TestingServer.entity.Answer;
import com.example.TestingServer.entity.Group;
import com.example.TestingServer.entity.Question;
import com.example.TestingServer.payload.request.dto.AnswerDTO;
import com.example.TestingServer.payload.request.dto.GroupDTO;

import java.util.List;

public interface GroupService {
    GroupDTO getGroupById(Long groupId);

    List<GroupDTO> getAll() throws Exception;

    GroupDTO create(GroupDTO groupDTO) throws Exception;

    GroupDTO update(GroupDTO groupDTO, Long groupId) throws Exception;

    void delete(Long groupId);
}
