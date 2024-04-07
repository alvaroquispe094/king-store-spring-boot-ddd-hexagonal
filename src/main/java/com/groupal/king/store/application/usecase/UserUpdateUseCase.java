package com.groupal.king.store.application.usecase;

import com.groupal.king.store.application.port.in.UpdateUserCommand;
import com.groupal.king.store.application.port.out.UserRepository;
import com.groupal.king.store.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserUpdateUseCase implements UpdateUserCommand {

    private final UserRepository userRepository;

    @Override
    public User execute(User user, Long id) {
        log.info(">> Execute use case update product with request domain: {}", user);

        var response = userRepository.updateUser(user, id);

        log.info("<< Use case successfully processed with response domain: {}", response);
        return response;
    }
}


