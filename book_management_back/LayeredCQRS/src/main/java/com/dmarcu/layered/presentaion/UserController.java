package com.dmarcu.layered.presentaion;

import com.dmarcu.layered.application.Bus;
import com.dmarcu.layered.application.exceptions.DuplicateUserException;
import com.dmarcu.layered.application.exceptions.UserNotFoundException;
import com.dmarcu.layered.application.commands.user.AddUserCommand;
import com.dmarcu.layered.application.commands.user.AddUserResult;
import com.dmarcu.layered.application.commands.user.LoginUserCommand;
import com.dmarcu.layered.application.commands.user.LoginUserResult;
import com.dmarcu.layered.application.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class UserController {

    private final Bus bus;

    public UserController(Bus bus) {
        this.bus = bus;
    }

    @PostMapping("/signup")
    public ResponseEntity<AddUserResult> signUp(@RequestBody AddUserCommand command) {
        return new ResponseEntity<>(bus.executeCommand(command), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginUserResult> login(@RequestBody LoginUserCommand command) {
        return new ResponseEntity<>(bus.executeCommand(command), HttpStatus.OK);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Error> handleUserNotFoundException(UserNotFoundException exception) {
        return new ResponseEntity<>(new Error(exception.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<Error> handleDuplicateUserException(DuplicateUserException exception) {
        return new ResponseEntity<>(new Error(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
