package com.groupal.king.store.application.usecase;

import com.groupal.king.store.application.port.in.GetUsersQuery;
import com.groupal.king.store.application.port.out.UserRepository;
import com.groupal.king.store.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetUsersUseCase implements GetUsersQuery {

    private final UserRepository userRepository;

    @Override
    public List<User> execute(String role) {
        log.info(">> Execute get users use case");
        List<User> results;

        if (role.isEmpty()) results = userRepository.findAll();
        else results = userRepository.findAllByRole(role);

        log.info("<< Get users data successfully processed with response {}", results);
        return results;
    }
}
