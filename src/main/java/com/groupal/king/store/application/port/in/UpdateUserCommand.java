package com.groupal.king.store.application.port.in;

import com.groupal.king.store.domain.User;

public interface UpdateUserCommand {

    User execute(User user, Long id);

}
