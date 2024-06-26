package com.groupal.king.store.application.port.out;

import com.groupal.king.store.domain.User;

import java.util.List;

public interface UserRepository {

    List<User> findAll();

    List<User> findAllByRole(String role);

    User createUser(User user);

    User findById(Long id);

    User updateUser(User user, Long id);

    Boolean existsByEmail(String email);

}
