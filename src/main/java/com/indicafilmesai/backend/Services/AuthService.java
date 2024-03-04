package com.indicafilmesai.backend.Services;

import com.indicafilmesai.backend.Config.JwtTokenService;
import com.indicafilmesai.backend.Config.UserDetailsImpl;
import com.indicafilmesai.backend.Dtos.*;
import com.indicafilmesai.backend.Models.User;
import com.indicafilmesai.backend.Repositories.RoleRepository;
import com.indicafilmesai.backend.Repositories.UserRepository;
import com.indicafilmesai.backend.Utils.GenerateCodeValidation;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;

    public RecoveryJwtTokenDto login(LoginUserDto loginUserDto) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserDto.email(), loginUserDto.password());


        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);


        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();



        return new RecoveryJwtTokenDto(jwtTokenService.generateToken(userDetails));
    }

    public RecoverySignUpDto signupUser(SignUpDto createUserDto) {

        String code = GenerateCodeValidation.generateCode();

        User newUser = User.builder()
                .email(createUserDto.email())
                .password(passwordEncoder.encode(createUserDto.password()))
                .role(roleRepository.getReferenceById(1L))
                .code(code)
                .build();
        mailService.sendSimpleMessage("joaomatheusantunes@gmail.com", "TESTE EMAIL SPRING BOOT", "Use o seguinte código para se cadastrar no nosso aplicativo: " + code);

        User userCreated = userRepository.save(newUser);
        return new RecoverySignUpDto(userCreated.getId(), createUserDto.email());
    }

    public RecoverySignUpDto signupStore(SignUpStoreDto createUserStore) {

        String code = GenerateCodeValidation.generateCode();

        User newUser = User.builder()
                .email(createUserStore.email())
                .password(passwordEncoder.encode(createUserStore.password()))
                .role(roleRepository.getReferenceById(2L))
                .code(code)
                .build();
        mailService.sendSimpleMessage("joaomatheusantunes@gmail.com", "TESTE EMAIL SPRING BOOT", "Use o seguinte código para se cadastrar no nosso aplicativo: " + code);

        User userCreated = userRepository.save(newUser);
        return new RecoverySignUpDto(userCreated.getId(), createUserStore.email());
    }


    public RecoveryValidationEmail validationAccountEmailSignUp(ValidationEmail validationEmail) throws BadRequestException {

        Optional<User> userFind = userRepository.findByEmailAndCode(validationEmail.email(), validationEmail.code());

        if (userFind.isPresent()){
            User user = userFind.get();
            user.setVerified(true);
            user.setCode(null);
            userRepository.save(user);
            return new RecoveryValidationEmail("Sucesso");
        } else {
            throw new BadRequestException("Código inválido");
        }

    }
}
