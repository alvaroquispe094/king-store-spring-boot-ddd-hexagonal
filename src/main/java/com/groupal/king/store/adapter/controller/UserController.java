package com.groupal.king.store.adapter.controller;

import com.groupal.king.store.adapter.controller.model.in.UserRequest;
import com.groupal.king.store.adapter.controller.model.out.UserResponse;
import com.groupal.king.store.application.port.in.CreateUserCommand;
import com.groupal.king.store.application.port.in.GetUserByIdQuery;
import com.groupal.king.store.application.port.in.GetUsersQuery;
import com.groupal.king.store.application.port.in.UpdateUserCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Users", description = "Users API Controller")
@RequestMapping("/api/v1/users")
public class UserController {

    private final CreateUserCommand createUserCommand;
    private final UpdateUserCommand updateUserCommand;
    private final GetUsersQuery getUsersQuery;
    private final GetUserByIdQuery getUserByIdQuery;

    @GetMapping
    @Operation(summary = "Find all", description = "Request to find all users")
    @ApiResponse(responseCode = "200", content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserResponse.class))))
    public List<UserResponse> getAllUserss(
            @RequestParam(required = false, defaultValue = "") String role
    ) {
        log.info(">>Execute controller");

        var response = UserResponse.listFromDomain(getUsersQuery.execute(role));

        log.info("<< Request successfully executed");
        return response;
    }

    @GetMapping("/{id}")
    @Operation(summary = "find by id", description = "Request to find a user by id")
    @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = UserResponse.class)) })
    public UserResponse getUserById(@PathVariable Long id) {
        log.info(">> Execute controller with parameter id = {}", id);

        var user = getUserByIdQuery.execute(id);
        var response = UserResponse.fromDomain(user);

        log.info("<< Request successfully executed with response {}", response);
        return response;
    }

    @PostMapping("")
    @Operation(summary = "Create", description = "Request to create a new user")
    @Parameter(name = "Authorization", description = "Token de autorización", in = ParameterIn.HEADER, required = true)
    @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = UserResponse.class)) })
    public UserResponse createUser(@RequestBody @Valid UserRequest userRequest) {
        log.info(">> Execute controller with body: {}", userRequest);

        var user = createUserCommand.execute(userRequest.toDomain());
        var response = UserResponse.fromDomain(user);

        log.info("<< Request successfully executed with response {}", response);
        return response;
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update", description = "Request to update a existing user")
    @Parameter(name = "Authorization", description = "Token de autorización", in = ParameterIn.HEADER, required = true)
    @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = UserResponse.class)) })
    public UserResponse updateUser(@PathVariable Long id, @Valid @RequestBody UserRequest userRequest) {
        log.info(">> Execute controller with body: {}", userRequest);

        var user = updateUserCommand.execute(userRequest.toDomain(), id);
        var response = UserResponse.fromDomain(user);

        log.info("<< Request successfully executed with response {}", response);
        return response;
    }

}
