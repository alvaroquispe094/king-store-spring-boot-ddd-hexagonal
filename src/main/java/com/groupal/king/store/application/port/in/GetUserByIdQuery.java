package com.groupal.king.store.application.port.in;


import com.groupal.king.store.domain.User;

public interface GetUserByIdQuery {

    User execute(Long id);

}
