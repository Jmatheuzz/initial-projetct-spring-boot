package com.indicafilmesai.backend.Controllers;

import com.indicafilmesai.backend.Dtos.*;
import com.indicafilmesai.backend.Services.AuthService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<RecoveryJwtTokenDto> login(@RequestBody LoginUserDto loginUserDto) {
        RecoveryJwtTokenDto token = authService.login(loginUserDto);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<RecoverySignUpDto> signupUp(@RequestBody SignUpDto signup) {
        RecoverySignUpDto userCreated = authService.signupUser(signup);
        return ResponseEntity.status(HttpStatus.OK).body(userCreated);
    }

    @PostMapping("/store/signup")
    public ResponseEntity<RecoverySignUpDto> signupUpStore(@RequestBody SignUpStoreDto signup) {
        RecoverySignUpDto userCreated = authService.signupStore(signup);
        return  ResponseEntity.status(HttpStatus.OK).body(userCreated);
    }

    @SneakyThrows
    @PostMapping("/validation-email-signup")
    public ResponseEntity<RecoveryValidationEmail> validationEmailSignUp(@RequestBody ValidationEmail validationEmail) {
        RecoveryValidationEmail validationEmailRecovery = authService.validationAccountEmailSignUp(validationEmail);
        return  ResponseEntity.status(HttpStatus.OK).body(validationEmailRecovery);
    }
}
