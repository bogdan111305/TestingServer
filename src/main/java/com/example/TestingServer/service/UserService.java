package com.example.TestingServer.service;

import com.example.TestingServer.specifications.PageSetting;
import com.example.TestingServer.payload.request.dto.UserDTO;
import com.example.TestingServer.entity.User;
import com.example.TestingServer.payload.request.SignupRequest;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

public interface UserService {
    UserDTO create(SignupRequest userDTO) throws Exception;

    UserDTO update(UserDTO userDTO, Principal principal) throws Exception;

    void delete(Long userId) throws Exception;

    void giveRole(Long userId, String role) throws Exception;

    UserDTO getCurrentUser(Principal principal) throws Exception;

    User getUserById(Long userId)throws Exception;

    List<UserDTO> getAllUsers(String name) throws Exception;
}
