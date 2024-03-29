package com.groupal.king.store.application.port.out;


import com.groupal.king.store.adapter.database.model.RefreshTokenModel;

import java.util.Optional;

public interface RefreshTokenRepository {

    Optional<RefreshTokenModel> findByToken(String token);

    RefreshTokenModel createRefreshToken(Long userId);

    RefreshTokenModel verifyExpiration(RefreshTokenModel token);

    int deleteByUserId(Long userId);

}
