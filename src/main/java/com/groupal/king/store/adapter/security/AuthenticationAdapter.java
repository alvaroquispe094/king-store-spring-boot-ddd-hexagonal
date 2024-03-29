package com.groupal.king.store.adapter.security;

import com.groupal.king.store.adapter.database.model.RefreshTokenModel;
import com.groupal.king.store.adapter.database.repository.RefreshTokenDataRepository;
import com.groupal.king.store.adapter.database.repository.UserDataRepository;
import com.groupal.king.store.adapter.security.exception.TokenRefreshException;
import com.groupal.king.store.application.port.out.RefreshTokenRepository;
import com.groupal.king.store.domain.RefreshToken;
import com.groupal.king.store.domain.User;
import com.groupal.king.store.domain.UserDetail;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Repository
@RequiredArgsConstructor
public class AuthenticationAdapter implements RefreshTokenRepository, UserDetailsService {

    @Value("${jwt.refreshExpiration}")
    private Long refreshTokenDurationMs;

    private final RefreshTokenDataRepository refreshTokenRepository;

    private final UserDataRepository userRepository;


    @Override
    //ToDo: Mejorar ese .get() y agragado manual de Optional.of()
    public Optional<RefreshToken> findByToken(String token) {
        return Optional.of(refreshTokenRepository.findByToken(token).get().toDomain());
    }

    @Override
    public RefreshToken createRefreshToken(Long userId) {
        RefreshTokenModel refreshToken = new RefreshTokenModel();

        refreshToken.setUser(userRepository.findById(userId).get());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken.toDomain();
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(RefreshTokenModel.fromDomain(token));
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }

        return token;
    }

    @Override
    @Transactional
    //ToDo: Mejorar todos los .get() de los optional
    public int deleteByUserId(Long userId) {
        return refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username))
                .toDomain();

        return UserDetail.build(user);
    }
}
