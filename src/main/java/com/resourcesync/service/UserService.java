package com.resourcesync.service;

import com.resourcesync.dto.UserDTO;
import com.resourcesync.dto.UserRequestDTO;

import java.util.List;

public interface UserService {
    UserDTO createUser(UserRequestDTO userRequestDTO);
    UserDTO getUserById(Long id);
    List<UserDTO> getAllUsers();
}
