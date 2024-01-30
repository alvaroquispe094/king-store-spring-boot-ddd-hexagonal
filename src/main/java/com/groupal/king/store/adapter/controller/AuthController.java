package com.groupal.king.store.adapter.controller;

import com.groupal.king.store.adapter.security.exception.TokenRefreshException;
import com.groupal.king.store.domain.enums.ERole;
import com.groupal.king.store.adapter.database.model.RefreshTokenModel;
import com.groupal.king.store.adapter.database.model.RoleModel;
import com.groupal.king.store.adapter.database.model.UserModel;
import com.groupal.king.store.domain.LoginRequest;
import com.groupal.king.store.domain.SignupRequest;
import com.groupal.king.store.domain.TokenRefreshRequest;
import com.groupal.king.store.adapter.security.model.JwtResponse;
import com.groupal.king.store.adapter.security.model.MessageResponse;
import com.groupal.king.store.adapter.security.model.TokenRefreshResponse;
import com.groupal.king.store.adapter.database.repository.RoleRepository;
import com.groupal.king.store.adapter.database.repository.UserDataRepository;
import com.groupal.king.store.adapter.security.jwt.JwtUtils;
import com.groupal.king.store.adapter.security.services.RefreshTokenService;
import com.groupal.king.store.adapter.security.services.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserDataRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @Autowired
  RefreshTokenService refreshTokenService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwt = jwtUtils.generateJwtToken(userDetails);

        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

        RefreshTokenModel refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(),
            userDetails.getUsername(), userDetails.getEmail(), roles));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshTokenModel::getUser)
                .map(user -> {
                  String token = jwtUtils.generateTokenFromUsername(user.getUsername());
                  RefreshTokenModel newRefreshToken = refreshTokenService.createRefreshToken(user.getId());
                  return ResponseEntity.ok(new TokenRefreshResponse(token, newRefreshToken.getToken()));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getEmail())) {
          return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
          return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        UserModel user = UserModel.builder()
                .firstname(signUpRequest.getFirstname())
                .lastname(signUpRequest.getLastname())
                .username(signUpRequest.getEmail())
                .email(signUpRequest.getEmail())
                .password(encoder.encode(signUpRequest.getPassword()))
                .gender(signUpRequest.getGender())
                .birthDate(signUpRequest.getBirthDate())
                .phone(signUpRequest.getPhone())
                .build();

        Set<String> strRoles = signUpRequest.getRole();
        Set<RoleModel> roles = new HashSet<>();

        if (strRoles == null) {
          RoleModel userRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(userRole);
        } else {
          strRoles.forEach(role -> {
              switch (role) {
                  case "admin" -> {
                      RoleModel adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                      roles.add(adminRole);
                  }
                  case "mod" -> {
                      RoleModel modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                      roles.add(modRole);
                  }
                  default -> {
                      RoleModel userRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
                              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                      roles.add(userRole);
                  }
              }
          });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
  
    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userDetails.getId();
        refreshTokenService.deleteByUserId(userId);
        return ResponseEntity.ok(new MessageResponse("Log out successful!"));
    }

}
