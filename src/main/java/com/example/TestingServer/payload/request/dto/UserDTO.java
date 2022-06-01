package com.example.TestingServer.payload.request.dto;

import com.example.TestingServer.payload.request.dto.transfer.Exist;
import com.example.TestingServer.payload.request.dto.transfer.New;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

@Data
public class UserDTO {
    @Null(groups = {New.class})
    @NotNull(groups = {Exist.class})
    private Long id;
    @NotEmpty(message = "Username not can empty", groups = {New.class, Exist.class})
    @Size(min = 8, groups = {New.class, Exist.class})
    private String username;
    @NotEmpty(message = "FirstName not can empty", groups = {New.class, Exist.class})
    @Size(min = 3, max = 15, groups = {New.class, Exist.class})
    private String firstName;
    @NotEmpty(message = "LastName not can empty", groups = {New.class, Exist.class})
    @Size(min = 3, max = 15, groups = {New.class, Exist.class})
    private String lastName;
    @NotNull(message = "Group is required")
    private GroupDTO group;
}
