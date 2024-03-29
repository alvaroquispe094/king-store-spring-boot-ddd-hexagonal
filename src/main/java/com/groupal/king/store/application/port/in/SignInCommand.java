package com.groupal.king.store.application.port.in;

import com.groupal.king.store.domain.JwtResponse;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

public interface SignInCommand {

    JwtResponse execute(Command command);

    @Value
    @Builder
    class Command {

        @NotBlank(message = "Email mustn't be blank")
        String email;
        @NotBlank(message = "Password mustn't be blank")
        String password;
    }
}
