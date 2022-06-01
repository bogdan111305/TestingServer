package com.example.TestingServer.web;

import com.example.TestingServer.payload.request.dto.UserDTO;
import com.example.TestingServer.payload.request.LoginRequest;
import com.example.TestingServer.payload.request.SignupRequest;
import com.example.TestingServer.payload.response.JWTTokenSuccessResponse;
import com.example.TestingServer.service.AuthenticatedServiceImpl;
import com.example.TestingServer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@PreAuthorize("permitAll()")
@CrossOrigin
public class AuthController {

    private final UserService userService;
    private final AuthenticatedServiceImpl authenticatedService;

    @Autowired
    public AuthController(UserService userService, AuthenticatedServiceImpl authenticatedService){
        this.userService = userService;
        this.authenticatedService = authenticatedService;
    }

    @PostMapping("/login")
    public JWTTokenSuccessResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
        return authenticatedService.authenticateUser(loginRequest);
    }

    @PostMapping("/register")
    public UserDTO registerUser(@Valid @RequestBody SignupRequest signupRequest) throws Exception {
        return userService.create(signupRequest);
    }

}
