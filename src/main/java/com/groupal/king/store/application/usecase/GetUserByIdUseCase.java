package com.groupal.king.store.application.usecase;

import com.groupal.king.store.application.port.in.GetUserByIdQuery;
import com.groupal.king.store.application.port.out.UserRepository;
import com.groupal.king.store.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetUserByIdUseCase implements GetUserByIdQuery {

    private final UserRepository userRepository;

    @Override
    public User execute(Long id) {
        log.info(">> Execute get user use case");

        User result = userRepository.findById(id);

        log.info("<< Get user data successfully processed with response {}", result);
        return result;
    }
}
