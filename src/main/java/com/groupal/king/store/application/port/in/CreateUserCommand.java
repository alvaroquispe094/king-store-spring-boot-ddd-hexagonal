package com.groupal.king.store.application.port.in;

import com.groupal.king.store.domain.User;

public interface CreateUserCommand {

    User execute(User user);

}
