package com.groupal.king.store.application.usecase;

import com.groupal.king.store.application.exception.EmailTakenException;
import com.groupal.king.store.application.port.in.CreateUserCommand;
import com.groupal.king.store.application.port.out.UserRepository;
import com.groupal.king.store.config.ErrorCode;
import com.groupal.king.store.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserCreateUseCase implements CreateUserCommand {

    private final UserRepository userRepository;

    @Override
    public User execute(Command command) {
        log.info(">> Execute use case create user with request domain: {}", command.toDomain());

        if (userRepository.existsByEmail(command.getEmail())) {
            throw new EmailTakenException(ErrorCode.INVALID_EMAIL_TAKEN);
        }

        var response = userRepository.createUser(command.toDomain());

        log.info("<< Use case successfully processed with response domain: {}", response);
        return response;
    }
}


