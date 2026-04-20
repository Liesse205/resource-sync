package com.resourcesync.controller;

import com.resourcesync.dto.UserDTO;
import com.resourcesync.dto.UserRequestDTO;
import com.resourcesync.dto.ResourceDTO;
import com.resourcesync.service.UserService;
import com.resourcesync.service.ResourceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ResourceService resourceService;

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserRequestDTO userRequestDTO) { return new ResponseEntity<>(userService.createUser(userRequestDTO), HttpStatus.CREATED); }
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) { return ResponseEntity.ok(userService.getUserById(id)); }
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() { return ResponseEntity.ok(userService.getAllUsers()); }
    @GetMapping("/{userId}/resources")
    public ResponseEntity<List<ResourceDTO>> getResourcesByUserId(@PathVariable Long userId) { return ResponseEntity.ok(resourceService.getResourcesByUserId(userId)); }
}
