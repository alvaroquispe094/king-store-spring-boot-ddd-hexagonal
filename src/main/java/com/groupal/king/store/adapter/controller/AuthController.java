package com.groupal.king.store.adapter.controller;

import com.groupal.king.store.adapter.security.model.MessageResponse;
import com.groupal.king.store.application.port.in.DeleteRefreshTokenQuery;
import com.groupal.king.store.application.port.in.RefreshTokenQuery;
import com.groupal.king.store.application.port.in.SignInCommand;
import com.groupal.king.store.application.port.in.SignUpCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final SignInCommand signInCommand;
    private final SignUpCommand signUpCommand;
    private final RefreshTokenQuery refreshTokenQuery;
    private final DeleteRefreshTokenQuery deleteRefreshTokenQuery;

    @PostMapping("/signin")
    public ResponseEntity<?> signInUser(@Valid @RequestBody SignInCommand.Command command) {

        var jtwResponse = signInCommand.execute(command);

        return ResponseEntity.ok(jtwResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@Valid @RequestParam String refreshToken) {

        var jtwResponse = refreshTokenQuery.execute(refreshToken);

        return ResponseEntity.ok(jtwResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUpUser(@Valid @RequestBody SignUpCommand.Command command) {

        signUpCommand.execute(command);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
  
    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        deleteRefreshTokenQuery.execute();
        return ResponseEntity.ok(new MessageResponse("Log out successful!"));
    }

}
