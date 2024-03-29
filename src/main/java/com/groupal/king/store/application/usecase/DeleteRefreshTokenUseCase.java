package com.groupal.king.store.application.usecase;

import com.groupal.king.store.domain.UserDetail;
import com.groupal.king.store.application.port.in.DeleteRefreshTokenQuery;
import com.groupal.king.store.application.port.out.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeleteRefreshTokenUseCase implements DeleteRefreshTokenQuery {

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void execute() {
        UserDetail userDetails = (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id = userDetails.getId();
        refreshTokenRepository.deleteByUserId(id);
    }
}


