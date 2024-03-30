package com.groupal.king.store.application.port.in;

import com.groupal.king.store.domain.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.util.Set;

public interface UpdateUserCommand {

    User execute(Command command, Long id);

    @Value
    @Builder
    class Command {

        Long id;

        @NotBlank(message = "Firstname mustn't be blank")
        String firstname;

        @NotBlank(message = "Lastname mustn't be blank")
        String lastname;

        @NotBlank(message = "Username mustn't be blank")
        String username;

        @NotBlank(message = "Email mustn't be blank")
        String email;

        @NotBlank(message = "Password mustn't be blank")
        String password;

        @NotBlank(message = "Gender mustn't be blank")
        String gender;

        @NotBlank(message = "Gender mustn't be blank")
        String birthDate;

        @NotBlank(message = "Phone mustn't be blank")
        String phone;

        @NotNull(message = "Roles mustn't be blank")
        Set<String> roles;

        public User toDomain() {
            return User.builder()
                    .id(id)
                    .firstname(firstname)
                    .lastname(lastname)
                    .username(username)
                    .email(email)
                    .password(password)
                    .gender(gender)
                    .birthDate(birthDate)
                    .phone(phone)
                    .roles(roles)
                    .build();
        }
    }
}
