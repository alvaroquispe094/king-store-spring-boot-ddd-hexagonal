package com.groupal.king.store.adapter.controller;

import com.groupal.king.store.adapter.controller.model.out.UserResponse;
import com.groupal.king.store.application.port.in.CreateUserCommand;
import com.groupal.king.store.application.port.in.GetUserByIdQuery;
import com.groupal.king.store.application.port.in.GetUsersQuery;
import com.groupal.king.store.application.port.in.UpdateUserCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final CreateUserCommand createUserCommand;
    private final UpdateUserCommand updateUserCommand;
    private final GetUsersQuery getUsersQuery;
    private final GetUserByIdQuery getUserByIdQuery;

    @GetMapping
    public List<UserResponse> getAllUserss(
            @RequestParam(required = false, defaultValue = "") String role
    ) {
        log.info(">>Execute controller");

        var response = UserResponse.listFromDomain(getUsersQuery.execute(role));

        log.info("<< Request successfully executed");
        return response;
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        log.info(">> Execute controller with parameter id = {}", id);

        var user = getUserByIdQuery.execute(id);
        var response = UserResponse.fromDomain(user);

        log.info("<< Request successfully executed with response {}", response);
        return response;
    }

    @PostMapping("")
    public UserResponse createUser(@RequestBody @Valid CreateUserCommand.Command command) {
        log.info(">> Execute controller with body: {}", command);

        var user = createUserCommand.execute(command);
        var response = UserResponse.fromDomain(user);

        log.info("<< Request successfully executed with response {}", response);
        return response;
    }

    @PutMapping("/{id}")
    public UserResponse updateUser(@PathVariable Long id, @Valid @RequestBody UpdateUserCommand.Command command) {
        log.info(">> Execute controller with body: {}", command);

        var user = updateUserCommand.execute(command, id);
        var response = UserResponse.fromDomain(user);

        log.info("<< Request successfully executed with response {}", response);
        return response;
    }

}
