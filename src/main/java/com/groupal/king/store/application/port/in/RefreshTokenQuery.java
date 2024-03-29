package com.groupal.king.store.application.port.in;


import com.groupal.king.store.adapter.security.model.TokenRefreshResponse;

public interface RefreshTokenQuery {

    TokenRefreshResponse execute(String token);

}
