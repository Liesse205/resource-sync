package com.resourcesync.service;
import com.resourcesync.dto.*;
import com.resourcesync.exception.UserNotFoundException;
import com.resourcesync.model.User;
import com.resourcesync.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public UserDTO createUser(UserRequestDTO request) {
        User user = User.builder().name(request.getName()).email(request.getEmail()).build();
        return mapToDTO(userRepository.save(user));
    }
    @Override
    public UserDTO getUserById(Long id) {
        return userRepository.findById(id).map(this::mapToDTO).orElseThrow(() -> new UserNotFoundException("User not found"));
    }
    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }
    private UserDTO mapToDTO(User user) {
        return UserDTO.builder().id(user.getId()).name(user.getName()).email(user.getEmail()).createdAt(user.getCreatedAt()).build();
    }
}
