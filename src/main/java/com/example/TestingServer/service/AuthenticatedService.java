package com.example.TestingServer.service;

import com.example.TestingServer.payload.request.LoginRequest;
import com.example.TestingServer.payload.response.JWTTokenSuccessResponse;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public interface AuthenticatedService {
    JWTTokenSuccessResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest);
}
