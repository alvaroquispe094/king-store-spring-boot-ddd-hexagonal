package com.groupal.king.store.application.port.out;

import com.groupal.king.store.adapter.database.model.UserModel;
import com.groupal.king.store.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> findAll();

    List<User> findAllByRole(String role);

    User createUser(User user);

    User findById(Long id);

    User updateUser(User user);

    Optional<UserModel> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

}
