package com.groupal.king.store.adapter.database;

import com.groupal.king.store.adapter.database.model.RoleModel;
import com.groupal.king.store.adapter.database.model.UserModel;
import com.groupal.king.store.adapter.database.repository.RoleDataRepository;
import com.groupal.king.store.adapter.database.repository.UserDataRepository;
import com.groupal.king.store.application.exception.NotFoundException;
import com.groupal.king.store.application.exception.RoleNotFoundException;
import com.groupal.king.store.application.port.out.UserRepository;
import com.groupal.king.store.config.ErrorCode;
import com.groupal.king.store.domain.User;
import com.groupal.king.store.domain.enums.ERole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserAdapter implements UserRepository {

    private final UserDataRepository repository;
    private final RoleDataRepository roleRepository;

    private final PasswordEncoder encoder;

    @Override
    public List<User> findAll() {
        log.info(">> Get all users from DB");

        var results = repository.findAll()
                .stream()
                .map(UserModel::toDomain)
                .collect(Collectors.toList());

        log.info("<< Get all users with response: {}", results);
        return results;
    }

    @Override
    public List<User> findAllByRole(String role) {
        log.info(">> Get all users from DB by Role");

        var results = repository.findAll()
                .stream()
                .map(UserModel::toDomain)
                .filter(item -> item.getRoles().contains(role))
                .collect(Collectors.toList());

        log.info("<< Get all users with response: {}", results);
        return results;
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
    public User createUser(User user) {
        log.info(">> Create a new user with: {}", user);

        var roles = this.mapRoles(user.getRoles());
        UserModel userModel = UserModel.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .username(user.getEmail())
                .email(user.getEmail())
                .password(encoder.encode(user.getPassword()))
                .gender(user.getGender())
                .birthDate(user.getBirthDate())
                .phone(user.getPhone())
                .roles(roles)
                .build();

        var response = repository.save(userModel);

        log.info("Saving in db a new user with response {}", response);
        return response.toDomain();
    }

    @Override
    public User updateUser(User user, Long id) {
        log.info(">> Update user with: {}", user);

        var roles = this.mapRoles(user.getRoles());
        UserModel userModel = UserModel.builder()
                .id(id)
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .username(user.getEmail())
                .email(user.getEmail())
                .password(encoder.encode(user.getPassword()))
                .gender(user.getGender())
                .birthDate(user.getBirthDate())
                .phone(user.getPhone())
                .roles(roles)
                .build();

        var response = repository.save(userModel);

        log.info("Update in db a user with response {}", response);
        return response.toDomain();
    }

    @Override
    public Boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    private Set<RoleModel> mapRoles(Set<String> roles){
        Set<RoleModel> roleList = new HashSet<>();

        if (roles == null) {
            RoleModel userRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
                    .orElseThrow(() -> new RoleNotFoundException(ErrorCode.USER_ROLE_NOT_FOUND, "Error: Role is not found."));
            roleList.add(userRole);
        } else {
            roles.forEach(role -> {
                switch (role) {
                    case "customer" -> {
                        RoleModel adminRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
                                .orElseThrow(() -> new RoleNotFoundException(ErrorCode.USER_ROLE_NOT_FOUND, "Error: Role is not found."));
                        roleList.add(adminRole);
                    }
                    case "mod" -> {
                        RoleModel modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RoleNotFoundException(ErrorCode.USER_ROLE_NOT_FOUND, "Error: Role is not found."));
                        roleList.add(modRole);
                    }
                    case "admin" -> {
                        RoleModel adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RoleNotFoundException(ErrorCode.USER_ROLE_NOT_FOUND, "Error: Role is not found."));
                        roleList.add(adminRole);
                    }
                    default -> throw new RoleNotFoundException(ErrorCode.USER_ROLE_NOT_FOUND, "Error: Role is not found.");
                }
            });
        }

        return roleList;
    }
}
