package com.groupal.king.store.adapter.controller.model;

import com.groupal.king.store.domain.User;
import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Value
@Builder
public class UserRest {
    Long id;
    String firstname;
    String lastname;
    String username;
    String email;
    String password;
    String gender;
    String birthDate;
    String phone;
    Set<String> roles;

    public static UserRest fromDomain(User user){
        return UserRest.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .username(user.getEmail())
                .email(user.getEmail())
                .password(user.getPassword())
                .gender(user.getGender())
                .birthDate(user.getBirthDate())
                .phone(user.getPhone())
                .roles(user.getRoles())
                .build();
    }

    public static List<UserRest> listFromDomain(List<User> users){
        return users.stream()
                        .map(UserRest::fromDomain)
                        .collect(Collectors.toList());
    }

}
