package com.groupal.king.store.application.usecase;

import com.groupal.king.store.domain.JwtResponse;
import com.groupal.king.store.domain.UserDetail;
import com.groupal.king.store.application.port.in.SignInCommand;
import com.groupal.king.store.application.port.out.JwtUtilsRepository;
import com.groupal.king.store.application.port.out.RefreshTokenRepository;
import com.groupal.king.store.domain.RefreshToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class SignInUseCase implements SignInCommand {

    private final AuthenticationManager authenticationManager;

    private final JwtUtilsRepository jwtUtils;

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public JwtResponse execute(Command command) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(command.getEmail(), command.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetail userDetails = (UserDetail) authentication.getPrincipal();

        String jwt = jwtUtils.generateJwtToken(userDetails);

        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        RefreshToken refreshToken = refreshTokenRepository.createRefreshToken(userDetails.getId());

        return new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(),
                userDetails.getUsername(), userDetails.getEmail(), roles);
    }
}


