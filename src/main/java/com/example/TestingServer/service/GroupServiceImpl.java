package com.example.TestingServer.service;

import com.example.TestingServer.entity.Group;
import com.example.TestingServer.exceptions.GroupNotFoundException;
import com.example.TestingServer.payload.request.dto.GroupDTO;
import com.example.TestingServer.repository.GroupRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupServiceImpl implements GroupService{
    private final GroupRepository groupRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public GroupServiceImpl(GroupRepository groupRepository, ModelMapper modelMapper) {
        this.groupRepository = groupRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public GroupDTO getGroupById(Long groupId) {
        Group group = groupRepository.findGroupById(groupId).orElseThrow(() -> new GroupNotFoundException("Group not found"));
        return modelMapper.map(group, GroupDTO.class);
    }

    @Override
    public List<GroupDTO> getAll() throws Exception {
        return groupRepository.findAll().stream().map(group -> modelMapper.map(group, GroupDTO.class)).collect(Collectors.toList());
    }

    @Override
    public GroupDTO create(GroupDTO groupDTO) throws Exception {
        Group group = new Group();
        group.setName(groupDTO.getName());
        group = groupRepository.save(group);
        return modelMapper.map(group, GroupDTO.class);
    }

    @Override
    public GroupDTO update(GroupDTO groupDTO, Long groupId) throws Exception {
        Group group = groupRepository.getById(groupId);
        group.setName(groupDTO.getName());
        group = groupRepository.save(group);
        return modelMapper.map(group, GroupDTO.class);
    }

    @Override
    public void delete(Long groupId) {
        groupRepository.deleteById(groupId);
    }
}
