package com.example.TestingServer.payload.request;

import com.example.TestingServer.entity.Group;
import com.example.TestingServer.payload.request.dto.GroupDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Setter @Getter
public class SignupRequest {

    @NotEmpty(message = "Please enter your firstname")
    private String firstName;
    @NotEmpty(message = "Please enter your lastname")
    private String lastName;
    @NotEmpty(message = "Please enter your username")
    private String username;
    @NotEmpty(message = "Password is required")
    @Size(min = 6)
    private String password;
    @Size(min = 6)
    private String confirmPassword;
    @NotNull(message = "Group is required")
    private GroupDTO group;
}
