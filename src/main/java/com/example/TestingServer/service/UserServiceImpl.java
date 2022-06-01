package com.example.TestingServer.service;

import com.example.TestingServer.entity.Group;
import com.example.TestingServer.repository.GroupRepository;
import com.example.TestingServer.payload.request.dto.UserDTO;
import com.example.TestingServer.entity.User;
import com.example.TestingServer.entity.enums.ERole;
import com.example.TestingServer.exceptions.UserExistException;
import com.example.TestingServer.payload.request.SignupRequest;
import com.example.TestingServer.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    public static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ModelMapper modelMapper;
    private final GroupRepository groupRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, ModelMapper modelMapper, GroupRepository groupRepository){
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.modelMapper = modelMapper;
        this.groupRepository = groupRepository;
    }

    public UserDTO create(SignupRequest userDTO) throws Exception {
        User user = modelMapper.map(userDTO, User.class);
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));

        Group group = null;
        if (userDTO.getGroup().getId() != null){
            group = groupRepository.getById(userDTO.getGroup().getId());
        } else {
            group = new Group();
            group.setName(userDTO.getGroup().getName());
            group = groupRepository.save(group);
        }
        user.setGroup(group);

        if(userRepository.count() > 0){
            user.getRoles().add(ERole.ROLE_USER);
        } else {
            user.getRoles().add(ERole.ROLE_ADMIN);
        }

        try {
            LOG.info("Saving User");
            User userCreated = userRepository.save(user);
            return modelMapper.map(userCreated, UserDTO.class);
        }catch (Exception e){
            LOG.error("Error during registration. {}" + e.getMessage());
            throw new UserExistException("The user "+ user.getUsername() + " already exist.");
        }
    }

    public UserDTO update(UserDTO userDTO, Principal principal) throws Exception {
        User user = getUserByPrincipal(principal);

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());

        Group group = groupRepository.getById(userDTO.getGroup().getId());
        user.setGroup(group);

        try {
            LOG.info("Updating User");
            User userCreated = userRepository.save(user);
            return modelMapper.map(userCreated, UserDTO.class);
        }catch (Exception e){
            LOG.error("Error during registration. {}" + e.getMessage());
            throw new UserExistException("The user "+ user.getUsername() + " already exist.");
        }
    }

    public void delete(Long userId) throws Exception {
        User user = getUserById(userId);

        try {
            LOG.info("Deleting User");
            userRepository.delete(user);
        }catch (Exception e){
            LOG.error("Error during registration. {}" + e.getMessage());
            throw new UserExistException("The user "+ user.getUsername() + " already exist.");
        }
    }

    @Override
    public void giveRole(Long userId, String role) throws Exception {
        User user = userRepository.getById(userId);

        user.getRoles().clear();

        switch (role.toUpperCase()) {
            case  ("ADMIN"):
                user.getRoles().add(ERole.ROLE_ADMIN);
                break;
            case ("TEACHER"):
                user.getRoles().add(ERole.ROLE_TEACHER);
                break;
            default:
                user.getRoles().add(ERole.ROLE_USER);
                break;
        }

        userRepository.save(user);
    }

    public UserDTO getCurrentUser(Principal principal) throws Exception{
        User user = getUserByPrincipal(principal);
        return modelMapper.map(user, UserDTO.class);
    }

    public User getUserById(Long userId){
        return userRepository.findUserById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found with username" + userId));
    }

    private User getUserByPrincipal(Principal principal)throws Exception{
        String username = principal.getName();
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username" + username));
    }

    public List<UserDTO> getAllUsers(String name) throws Exception{

        List<User> users;

        if(name == null){
            users = userRepository.findAll();
        } else {
            users = userRepository.findUserByGroupName(name);
        }

        return users.stream().map((user) -> modelMapper.map(user, UserDTO.class)).collect(Collectors.toList());
    }
}
