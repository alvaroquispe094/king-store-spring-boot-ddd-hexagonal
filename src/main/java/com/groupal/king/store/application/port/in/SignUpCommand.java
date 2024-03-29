package com.groupal.king.store.application.port.in;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

import java.util.Set;

public interface SignUpCommand {

    void execute(Command command);

    @Value
    @Builder
    class Command {

        @NotBlank
        @Size(min = 3, max = 20)
        String firstname;

        @NotBlank
        @Size(min = 3, max = 20)
        String lastname;

        @NotBlank
        @Size(max = 50)
        @Email
        String email;

        @NotBlank
        @Size(min = 3, max = 20)
        String gender;

        @NotBlank
        @Size(min = 3, max = 20)
        String birthDate;

        @NotBlank
        @Size(min = 3, max = 20)
        String phone;

        Set<String> role;

        @NotBlank
        @Size(min = 6, max = 40)
        String password;
    }
}
