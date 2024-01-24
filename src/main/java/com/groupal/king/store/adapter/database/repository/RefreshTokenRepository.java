package com.groupal.king.store.adapter.database.repository;


import com.groupal.king.store.adapter.database.model.RefreshTokenModel;
import com.groupal.king.store.adapter.database.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenModel, Long> {
  Optional<RefreshTokenModel> findByToken(String token);

  @Modifying
  int deleteByUser(UserModel user);
}