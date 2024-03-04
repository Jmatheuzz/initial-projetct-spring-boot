package com.indicafilmesai.backend.Services;

import com.indicafilmesai.backend.Config.JwtTokenService;
import com.indicafilmesai.backend.Config.UserAuthenticationFilter;
import com.indicafilmesai.backend.Config.UserDetailsImpl;
import com.indicafilmesai.backend.Dtos.*;
import com.indicafilmesai.backend.Models.Role;
import com.indicafilmesai.backend.Models.User;
import com.indicafilmesai.backend.Repositories.UserRepository;
import org.apache.coyote.BadRequestException;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserAuthenticationFilter userAuthenticationFilter;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;



    public RecoveryCreateUser createUser(CreateUserDto createUserDto) {


        User newUser = User.builder()
                .email(createUserDto.email())

                .password(passwordEncoder.encode(createUserDto.password()))
                .role(createUserDto.role())
                .build();
        mailService.sendSimpleMessage("joaomatheusantunes@gmail.com", "TESTE EMAIL SPRING BOOT", "Use o seguinte código para se cadastrar no nosso aplicativo: ");

        User userCreated = userRepository.save(newUser);
        return new RecoveryCreateUser(userCreated.getId(), userCreated.getEmail(), userCreated.getRole());
    }

    public RecoveryUpdateUser updatedUser(UpdateUserDto updateUserDto) throws BadRequestException {
        Optional<User> userFind = userRepository.findByEmail(getCurrentEmail());

        if (userFind.isPresent()){
            User user = userFind.get();
            user.setEmail(updateUserDto.email());
            User userUpdated = userRepository.save(user);
            return new RecoveryUpdateUser(userUpdated.getId(), userUpdated.getEmail(), userUpdated.getRole());
        } else {
            throw new BadRequestException("Usuário não encontrado");
        }
    }

    public RecoveryDeleteUser deleteUser() throws BadRequestException {
        Optional<User> userFind = userRepository.findByEmail(getCurrentEmail());

        if (userFind.isPresent()){
            User user = userFind.get();
            userRepository.delete(user);
            return new RecoveryDeleteUser("Sucesso");
        } else {
            throw new BadRequestException("Usuário não encontrado");
        }
    }
    public String getCurrentEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
