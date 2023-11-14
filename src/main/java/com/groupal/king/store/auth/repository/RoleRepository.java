package com.groupal.king.store.auth.repository;

import com.groupal.king.store.auth.models.ERole;
import com.groupal.king.store.auth.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}
