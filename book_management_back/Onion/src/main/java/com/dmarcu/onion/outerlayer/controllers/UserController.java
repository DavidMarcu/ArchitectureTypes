package com.dmarcu.onion.outerlayer.controllers;

import com.dmarcu.onion.application.UserService;
import com.dmarcu.onion.application.exceptions.DuplicateUserException;
import com.dmarcu.onion.application.exceptions.UserNotFoundException;
import com.dmarcu.onion.domain.User;
import com.dmarcu.onion.outerlayer.dtos.ErrorResponse;
import com.dmarcu.onion.outerlayer.dtos.UserLoginResponse;
import com.dmarcu.onion.outerlayer.dtos.UserRequest;
import com.dmarcu.onion.outerlayer.mappers.UserMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@RequestBody UserRequest userRequest) {
        userService.addUser(userMapper.requestToDomain(userRequest));
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserRequest userRequest) {
        User user = userService.findUser(userMapper.loginRequestToDomain(userRequest));
        return new ResponseEntity<>(userMapper.domainToLoginResponse(user), HttpStatus.OK);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException exception) {
        return new ResponseEntity<>(new ErrorResponse(exception.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateUserException(DuplicateUserException exception) {
        return new ResponseEntity<>(new ErrorResponse(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
