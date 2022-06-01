package com.example.TestingServer.web;

import com.example.TestingServer.entity.Group;
import com.example.TestingServer.payload.request.dto.GroupDTO;
import com.example.TestingServer.payload.request.dto.QuestionDTO;
import com.example.TestingServer.payload.request.dto.transfer.Exist;
import com.example.TestingServer.payload.request.dto.transfer.New;
import com.example.TestingServer.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/group")
@PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
@CrossOrigin
public class GroupController {

    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping("")
    public GroupDTO createGroup(@Validated(New.class) @RequestBody GroupDTO groupDTO) throws Exception {
        return groupService.create(groupDTO);
    }

    @PutMapping("/{groupId}")
    public GroupDTO updateGroup(@Validated(Exist.class) @RequestBody GroupDTO groupDTO, @PathVariable(name = "groupId") Long groupId) throws Exception {
        return groupService.update(groupDTO, groupId);
    }

    @DeleteMapping("/{groupId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteGroup(@PathVariable("groupId") Long groupId) throws Exception {
        groupService.delete(groupId);
    }

    @GetMapping("/{groupId}")
    public GroupDTO getGroupById(@PathVariable Long groupId) throws Exception{
        return groupService.getGroupById(groupId);
    }
}