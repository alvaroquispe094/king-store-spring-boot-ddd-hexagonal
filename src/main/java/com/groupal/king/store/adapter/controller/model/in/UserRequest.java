package com.groupal.king.store.adapter.controller.model.in;

import com.groupal.king.store.domain.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.util.Set;

@Value
@Builder
public class UserRequest {

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
    @NotNull(message = "Roles mustn't be null")
    Set<String> roles;

    public User toDomain() {
        return User.builder()
                .firstname(firstname)
                .lastname(lastname)
                .username(username)
                .email(email)
                .password(password)
                .birthDate(birthDate)
                .gender(gender)
                .phone(phone)
                .roles(roles)
                .build();
    }
}
