package com.groupal.king.store.adapter.security.services;

import com.groupal.king.store.adapter.security.exception.TokenRefreshException;
import com.groupal.king.store.adapter.database.model.RefreshTokenModel;
import com.groupal.king.store.adapter.database.repository.RefreshTokenRepository;
import com.groupal.king.store.adapter.database.repository.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
  @Value("${jwt.refreshExpiration}")
  private Long refreshTokenDurationMs;

  @Autowired
  private RefreshTokenRepository refreshTokenRepository;

  @Autowired
  private UserDataRepository userRepository;

  public Optional<RefreshTokenModel> findByToken(String token) {
    return refreshTokenRepository.findByToken(token);
  }

  public RefreshTokenModel createRefreshToken(Long userId) {
    RefreshTokenModel refreshToken = new RefreshTokenModel();

    refreshToken.setUser(userRepository.findById(userId).get());
    refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
    refreshToken.setToken(UUID.randomUUID().toString());

    refreshToken = refreshTokenRepository.save(refreshToken);
    return refreshToken;
  }

  public RefreshTokenModel verifyExpiration(RefreshTokenModel token) {
    if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
      refreshTokenRepository.delete(token);
      throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
    }

    return token;
  }

  @Transactional
  public int deleteByUserId(Long userId) {
    return refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
  }
}
