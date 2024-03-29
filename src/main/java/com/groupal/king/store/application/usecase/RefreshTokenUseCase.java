package com.groupal.king.store.application.usecase;

import com.groupal.king.store.domain.TokenRefreshResponse;
import com.groupal.king.store.application.exception.GenerateTokenException;
import com.groupal.king.store.application.port.in.RefreshTokenQuery;
import com.groupal.king.store.application.port.out.JwtUtilsRepository;
import com.groupal.king.store.application.port.out.RefreshTokenRepository;
import com.groupal.king.store.config.ErrorCode;
import com.groupal.king.store.domain.RefreshToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RefreshTokenUseCase implements RefreshTokenQuery {

    private final JwtUtilsRepository jwtUtils;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public TokenRefreshResponse execute(String token) {

        return refreshTokenRepository.findByToken(token)
                .map(refreshTokenRepository::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String newToken = jwtUtils.generateTokenFromUsername(user.getUsername());
                    RefreshToken newRefreshToken = refreshTokenRepository.createRefreshToken(user.getId());
                    return new TokenRefreshResponse(newToken, newRefreshToken.getToken());
                })
                .orElseThrow(() -> new GenerateTokenException(ErrorCode.GENERATE_TOKEN_ERROR,
                        "Refresh token is not in database!"));
    }
}


