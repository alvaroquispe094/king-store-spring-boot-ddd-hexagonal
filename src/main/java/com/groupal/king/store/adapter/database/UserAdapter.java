package com.groupal.king.store.adapter.database;

import com.groupal.king.store.adapter.database.model.UserModel;
import com.groupal.king.store.adapter.database.repository.UserDataRepository;
import com.groupal.king.store.application.exception.NotFoundException;
import com.groupal.king.store.application.port.out.UserRepository;
import com.groupal.king.store.config.ErrorCode;
import com.groupal.king.store.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserAdapter implements UserRepository {

    private final UserDataRepository repository;

    @Override
    public List<User> findAll() {
        log.info(">> Get all products from DB");

        var results = repository.findAll()
                .stream()
                .map(UserModel::toDomain)
                .collect(Collectors.toList());

        log.info("<< Get all products with response: {}", results);
        return results;
    }

    @Override
    public User createUser(User user) {
        log.info(">> Create a new user with: {}", user);

        UserModel userModel = UserModel.fromDomain(user);
        var response = repository.save(userModel);

        log.info("Saving in db a new user with response {}", response);
        return response.toDomain();
    }

    @Override
    public User findById(Long id) {
        log.info(">> find user by id = {}", id);

        var result = this.repository.findById(id)
                .map(UserModel::toDomain)
                .orElseThrow( () -> new NotFoundException(ErrorCode.NOT_FOUND_EXCEPTION));

        log.info("<< Find user with response: {}", result);
        return result;
    }

    @Override
    public User updateUser(User user) {
        log.info(">> Update user with: {}", user);

        UserModel userModel = UserModel.fromDomain(user);
        var response = repository.save(userModel);

        log.info("Update in db a user with response {}", response);
        return response.toDomain();
    }
}
