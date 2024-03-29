package com.groupal.king.store.adapter.database.repository;

import com.groupal.king.store.adapter.database.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDataRepository extends JpaRepository<UserModel, Long> {
  Optional<UserModel> findByUsername(String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);
}
