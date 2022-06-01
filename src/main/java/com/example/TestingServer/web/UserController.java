package com.example.TestingServer.web;

import com.example.TestingServer.specifications.PageSetting;
import com.example.TestingServer.payload.request.dto.UserDTO;
import com.example.TestingServer.payload.request.dto.transfer.Exist;
import com.example.TestingServer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("")
    public UserDTO getUserProfile(Principal principal) throws Exception{
        return userService.getCurrentUser(principal);
    }

    @PutMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public UserDTO updateUser(@Validated(Exist.class) @RequestBody UserDTO userDTO, Principal principal) throws Exception {
        return userService.update(userDTO, principal);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(@PathVariable("userId")Long userId) throws Exception {
        userService.delete(userId);
    }

    @PostMapping("/role")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public void giveRole(@RequestParam(value = "id", required = false) Long userId,
                         @RequestParam(value = "group", required = false) String role) throws Exception {
        userService.giveRole(userId, role);
    }

    @GetMapping("/filter")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    public List<UserDTO> getAllUsers(@RequestParam(value = "group", required = false) String group) throws Exception{
        return userService.getAllUsers(group);
    }
}
