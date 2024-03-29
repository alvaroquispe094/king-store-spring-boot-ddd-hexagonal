package com.groupal.king.store.application.usecase;

import com.groupal.king.store.application.exception.EmailTakenException;
import com.groupal.king.store.application.port.in.SignUpCommand;
import com.groupal.king.store.application.port.out.UserRepository;
import com.groupal.king.store.config.ErrorCode;
import com.groupal.king.store.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SignUpUseCase implements SignUpCommand {

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    @Override
    public void execute(Command command) {

        if (userRepository.existsByEmail(command.getEmail())) {
            throw new EmailTakenException(ErrorCode.INVALID_EMAIL_TAKEN);
        }

        // Create new user's account
        User user = User.builder()
                .firstname(command.getFirstname())
                .lastname(command.getLastname())
                .username(command.getEmail())
                .email(command.getEmail())
                .password(encoder.encode(command.getPassword()))
                .gender(command.getGender())
                .birthDate(command.getBirthDate())
                .phone(command.getPhone())
                .roles(command.getRole())
                .build();

        userRepository.createUser(user);

    }
}


