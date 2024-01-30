package com.groupal.king.store.domain;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class User {
    Long id;
    String firstname;
    String lastname;
    String username;
    String email;
    String password;
    String gender;
    String birthDate;
    String phone;
    String role;

}
