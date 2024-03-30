package com.groupal.king.store.adapter.controller;

import com.groupal.king.store.adapter.security.model.MessageResponse;
import com.groupal.king.store.application.port.in.*;
import com.groupal.king.store.domain.JwtResponse;
import com.groupal.king.store.domain.TokenRefreshResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication API Controller")
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final SignInCommand signInCommand;
    private final SignUpCommand signUpCommand;
    private final RefreshTokenQuery refreshTokenQuery;
    private final DeleteRefreshTokenQuery deleteRefreshTokenQuery;

    @PostMapping("/signin")
    @Operation(summary = "Sign in", description = "Logging in system")
    /*@io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(schema= @Schema(implementation = SignInCommand.Command.class))
    )*/
    @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = JwtResponse.class), mediaType = "application/json") })
    public ResponseEntity<?> signInUser(@Valid @RequestBody SignInCommand.Command command) {

        var jtwResponse = signInCommand.execute(command);

        return ResponseEntity.ok(jtwResponse);
    }

    @PostMapping("/refresh")
    @Parameters(@Parameter(name = "refreshToken", description = "Token refresh", required = true))
    @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = TokenRefreshResponse.class), mediaType = "application/json") })
    public ResponseEntity<?> refreshToken(@Valid @RequestParam(required = true) String refreshToken) {

        var jtwResponse = refreshTokenQuery.execute(refreshToken);

        return ResponseEntity.ok(jtwResponse);
    }

    // ToDo: Validar el uso de @io.swagger.v3.oas.annotations.parameters.RequestBody, da problemas al mostrar en swagger
    @PostMapping("/signup")
    /*@io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(schema= @Schema(implementation = SignUpCommand.Command.class))
    )*/
    @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = MessageResponse.class), mediaType = "application/json") })
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
