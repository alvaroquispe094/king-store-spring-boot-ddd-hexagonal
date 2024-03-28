package com.groupal.king.store.adapter.controller;

import com.groupal.king.store.adapter.controller.model.UserRest;
import com.groupal.king.store.application.port.in.CreateUserCommand;
import com.groupal.king.store.application.port.in.GetUserByIdQuery;
import com.groupal.king.store.application.port.in.GetUsersQuery;
import com.groupal.king.store.application.port.in.UpdateUserCommand;
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
    public List<UserRest> getAllUserss(
            @RequestParam(required = false, defaultValue = "") String role
    ) {
        log.info(">>Execute controller");

        var response = UserRest.listFromDomain(getUsersQuery.execute(role));

        log.info("<< Request successfully executed");
        return response;
    }

    @GetMapping("/{id}")
    public UserRest getUserById(@PathVariable Long id) {
        log.info(">> Execute controller with parameter id = {}", id);

        var user = getUserByIdQuery.execute(id);
        var response = UserRest.fromDomain(user);

        log.info("<< Request successfully executed with response {}", response);
        return response;
    }

    @PostMapping("")
    public UserRest createUser(@RequestBody CreateUserCommand.Command command) {
        log.info(">> Execute controller with body: {}", command);

        var user = createUserCommand.execute(command);
        var response = UserRest.fromDomain(user);

        log.info("<< Request successfully executed with response {}", response);
        return response;
    }

    @PutMapping("/{id}")
    public UserRest updateUser(@PathVariable Long id, @RequestBody UpdateUserCommand.Command command) {
        log.info(">> Execute controller with body: {}", command);

        var user = updateUserCommand.execute(command, id);
        var response = UserRest.fromDomain(user);

        log.info("<< Request successfully executed with response {}", response);
        return response;
    }

}
