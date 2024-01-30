package com.groupal.king.store.application.port.in;


import com.groupal.king.store.domain.User;

import java.util.List;

public interface GetUsersQuery {

    List<User> execute();

}
