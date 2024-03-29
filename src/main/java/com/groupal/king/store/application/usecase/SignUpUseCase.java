package com.groupal.king.store.application.usecase;

import com.groupal.king.store.adapter.database.model.RoleModel;
import com.groupal.king.store.adapter.database.model.UserModel;
import com.groupal.king.store.adapter.database.repository.RoleDataRepository;
import com.groupal.king.store.adapter.database.repository.UserDataRepository;
import com.groupal.king.store.application.exception.EmailTakenException;
import com.groupal.king.store.application.exception.RoleNotFoundException;
import com.groupal.king.store.application.port.in.SignUpCommand;
import com.groupal.king.store.config.ErrorCode;
import com.groupal.king.store.domain.enums.ERole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class SignUpUseCase implements SignUpCommand {

    private final UserDataRepository userRepository;

    private final RoleDataRepository roleRepository;

    private final PasswordEncoder encoder;

    @Override
    public void execute(Command command) {

        if (userRepository.existsByEmail(command.getEmail())) {
            throw new EmailTakenException(ErrorCode.INVALID_EMAIL_TAKEN);
        }

        // Create new user's account
        UserModel user = UserModel.builder()
                .firstname(command.getFirstname())
                .lastname(command.getLastname())
                .username(command.getEmail())
                .email(command.getEmail())
                .password(encoder.encode(command.getPassword()))
                .gender(command.getGender())
                .birthDate(command.getBirthDate())
                .phone(command.getPhone())
                .build();

        Set<String> strRoles = command.getRole();
        Set<RoleModel> roles = new HashSet<>();

        if (strRoles == null) {
            RoleModel userRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
                    .orElseThrow(() -> new RoleNotFoundException(ErrorCode.USER_ROLE_NOT_FOUND, "Error: Role is not found."));
            roles.add(userRole);
        } else {
            for (String role : strRoles) {
                switch (role) {
                    case "customer" -> {
                        RoleModel adminRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
                                .orElseThrow(() -> new RoleNotFoundException(ErrorCode.USER_ROLE_NOT_FOUND, "Error: Role is not found."));
                        roles.add(adminRole);
                    }
                    case "mod" -> {
                        RoleModel modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RoleNotFoundException(ErrorCode.USER_ROLE_NOT_FOUND, "Error: Role is not found."));
                        roles.add(modRole);
                    }
                    case "admin" -> {
                        RoleModel adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RoleNotFoundException(ErrorCode.USER_ROLE_NOT_FOUND, "Error: Role is not found."));
                        roles.add(adminRole);
                    }
                    default -> throw new RoleNotFoundException(ErrorCode.USER_ROLE_NOT_FOUND);
                }
            }
        }

        user.setRoles(roles);
        userRepository.save(user);

    }
}


