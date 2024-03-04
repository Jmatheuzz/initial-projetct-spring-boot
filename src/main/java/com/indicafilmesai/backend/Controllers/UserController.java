package com.indicafilmesai.backend.Controllers;

import com.indicafilmesai.backend.Dtos.*;
import com.indicafilmesai.backend.Services.UserService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping
    public ResponseEntity<RecoveryCreateUser> createUser(@RequestBody CreateUserDto createUserDto) {
        RecoveryCreateUser createdUser = userService.createUser(createUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }


    @PutMapping
    public ResponseEntity<RecoveryUpdateUser> updatedUser(@RequestBody UpdateUserDto updateUserDto) throws BadRequestException {
        RecoveryUpdateUser updatedUser = userService.updatedUser(updateUserDto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }


    @DeleteMapping
    public ResponseEntity<RecoveryDeleteUser> deleteUser() throws BadRequestException {
        RecoveryDeleteUser response = userService.deleteUser();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
