package com.groupal.king.store.adapter.database.repository;

import com.groupal.king.store.domain.enums.ERole;
import com.groupal.king.store.adapter.database.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}
