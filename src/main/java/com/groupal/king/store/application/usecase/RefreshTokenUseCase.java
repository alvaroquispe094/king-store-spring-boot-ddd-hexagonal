package com.groupal.king.store.application.usecase;

import com.groupal.king.store.adapter.database.model.RefreshTokenModel;
import com.groupal.king.store.adapter.security.jwt.JwtUtils;
import com.groupal.king.store.adapter.security.model.TokenRefreshResponse;
import com.groupal.king.store.application.exception.GenerateTokenException;
import com.groupal.king.store.application.port.in.RefreshTokenQuery;
import com.groupal.king.store.application.port.out.RefreshTokenRepository;
import com.groupal.king.store.config.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RefreshTokenUseCase implements RefreshTokenQuery {

    private final JwtUtils jwtUtils;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public TokenRefreshResponse execute(String token) {
        String requestRefreshToken = token;

        return refreshTokenRepository.findByToken(requestRefreshToken)
                .map(refreshTokenRepository::verifyExpiration)
                .map(RefreshTokenModel::getUser)
                .map(user -> {
                    String newToken = jwtUtils.generateTokenFromUsername(user.getUsername());
                    RefreshTokenModel newRefreshToken = refreshTokenRepository.createRefreshToken(user.getId());
                    return new TokenRefreshResponse(newToken, newRefreshToken.getToken());
                })
                .orElseThrow(() -> new GenerateTokenException(ErrorCode.GENERATE_TOKEN_ERROR,
                        "Refresh token is not in database!"));
    }
}


