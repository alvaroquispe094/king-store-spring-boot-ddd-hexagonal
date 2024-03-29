package com.groupal.king.store.application.port.out;

import com.groupal.king.store.domain.UserDetail;

public interface JwtUtilsRepository {

    String generateJwtToken(UserDetail userPrincipal);

    String generateTokenFromUsername(String username);

    String getUserNameFromJwtToken(String token) ;

    boolean validateJwtToken(String authToken) ;

}
