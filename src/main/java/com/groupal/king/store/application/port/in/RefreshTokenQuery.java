package com.groupal.king.store.application.port.in;


import com.groupal.king.store.domain.TokenRefreshResponse;

public interface RefreshTokenQuery {

    TokenRefreshResponse execute(String token);

}
